package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    /** 싱글톤 빈에 주입된 프로토타입 빈은 싱글톤 빈 내에서 유지됨 */
    @Test
    void singletonClientUsePrototype() {
        ApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }

    /** 여러 싱글톤 빈에서 주입받는 프로토타입 빈은 각각 새로운 프로토타입 빈이 생성되어 주입됨 */
    @Test
    void injectedPrototypeBeanForEachSingleton() {
        ApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class, ClientBean2.class, PrototypeBean.class);
        ClientBean clientBean = ac.getBean(ClientBean.class);
        ClientBean2 clientBean2 = ac.getBean(ClientBean2.class);

        assertThat(clientBean.prototypeBean).isNotSameAs(clientBean2.prototypeBean);
    }


    @Scope("singleton")
    static class ClientBean {
        private PrototypeBean prototypeBean; //ClientBean의 생성시점에 주입된 후 유지 x01

        @Autowired
        public ClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider;
        private Provider<PrototypeBean> prototypeBeanProvider;

        public int logic() {
//            prototypeBean = prototypeBeanProvider.getObject(); //provider에서 새로운 포로토타입 빈을 받음
            prototypeBean = prototypeBeanProvider.get(); //JSR-330 Provider 사용
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }
    @Scope("singleton")
    static class ClientBean2 {
        private final PrototypeBean prototypeBean; //ClientBean의 생성시점에 주입된 후 유지 x02

        @Autowired
        public ClientBean2(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }


        public int logic() {
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init" + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }


    }
}
