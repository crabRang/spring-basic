package hello.core.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient /* implements InitializingBean, DisposableBean */ {

    private String url;

    public NetworkClient(){
        System.out.println("생성자 호출, url = "+url);
        connect();
        call("초기화 연결 메시지");
    }
    public void setUrl(String url) {
        //외부에서 url입력가능 하도록 setter생성
        this.url = url;
    }

    //서비스 시작 시 호출
    public void connect(){
        System.out.println("connect = "+url);
    }
    public void call(String message){
        System.out.println("call = "+url+", message = "+message);
    }

    //서비스 종료 시 호출
    public void disconnect(){
        System.out.println("close = "+url);
    }

/*
    방법1. InitializingBean, DisposableBean 인터페이스 사용
    @Override //InitializingBean : 초기화 지원
    public void afterPropertiesSet() throws Exception {
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초기화 연결 메시지");
    }

    @Override //DisposableBean : 소멸을 지원
    public void destroy() throws Exception {
        System.out.println("NetworkClient.destroy");
        disconnect();
    }
 */

/*
    방법2. 빈등록 초기화, 소멸 메서드 지정 -> 설정정보 사용, @Bean(initMethod = "init", destroyMethod = "close")
    방법3. 애노테이션 @PostContruct, @PreDestroy 사용 (가장권장하는 방법)
          스프링에 종속된 기술이 아니기 떄문에 스프링이 아닌 다른 컨테이너에서도 동작한다.
*/
    @PostConstruct
    public void init(){
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    @PreDestroy
    public void close(){
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
