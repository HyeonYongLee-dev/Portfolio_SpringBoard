# 스프링 프레임워크를 이용한 게시판 포트폴리오 - 개인 프로젝트
## 목적
<p> 1. 신입 개발자의 기본 소양 배양.</p>
<p> 2. 기본적인 crud 기능에 여러 기능을 조합한 게시판을 제작.</p>
<p> 3. 반복적인 연습을 통해 개발 요구사항에 최적화 될 수 있는 개발속도를 배양.</p>
<hr contenteditable="false" data-ke-type="horizontalRule" data-ke-style="style5" />

## 개발환경
>Front end
- jQuery 3

>Back end
- Spring Framework 3.0.6
- MyBatis 3.5.6
- Tomcat 8.5
- Apache Maven 3.6.3
- Oracle 11.2.0.1.0
- JDK 1.7
<hr contenteditable="false" data-ke-type="horizontalRule" data-ke-style="style5" />

## 데이터 베이스 모델링
![image](https://user-images.githubusercontent.com/73808497/111079560-dd771400-853d-11eb-85d6-53dc2701de35.png)
<hr contenteditable="false" data-ke-type="horizontalRule" data-ke-style="style5" />

## 패키지 구조
![src](https://user-images.githubusercontent.com/73808497/111080028-e537b800-853f-11eb-805a-6a6c25b86689.JPG)

![mapper](https://user-images.githubusercontent.com/73808497/111080043-f08ae380-853f-11eb-9c53-61e6e3371f6a.JPG)

![view](https://user-images.githubusercontent.com/73808497/111080065-08626780-8540-11eb-8a05-e06b98abd315.JPG)

<hr contenteditable="false" data-ke-type="horizontalRule" data-ke-style="style5" />

## 기능
# 회원단
1. 로그인 기능
![image](https://user-images.githubusercontent.com/73808497/111083195-9c87fb00-854f-11eb-857c-bef197b541cd.png)
![image](https://user-images.githubusercontent.com/73808497/111083245-e40e8700-854f-11eb-9b7f-c24cb78d9c27.png)

세션으로 로그인 정보를 보내어, 로그인이 성공된다. 로그인시, 로그아웃 태그 생성

2. 아이디/비밀번호 식별 기능
![image](https://user-images.githubusercontent.com/73808497/111083607-b88c9c00-8551-11eb-8c29-83f382774f0e.png)
회원 가입이 되지 않은 아이디/비밀번호 입력시
![image](https://user-images.githubusercontent.com/73808497/111083629-ca6e3f00-8551-11eb-9d21-041eb41bbc82.png)
자바스크립트의 alert 메서드를 이용한 알림

3. 회원 가입 기능
![image](https://user-images.githubusercontent.com/73808497/111083351-8169bb00-8550-11eb-85a7-6b18a868320e.png)
![image](https://user-images.githubusercontent.com/73808497/111083485-40be7180-8551-11eb-862e-c825c7632168.png)
![image](https://user-images.githubusercontent.com/73808497/111083508-4fa52400-8551-11eb-80cc-3bae4d6993b3.png)

회원 가입 태그 클릭시 회원 가입 폼 생성 및 가입하기 클릭시 회원 가입 완료

4 회원 삭제 기능
회원 리스트에서 삭제 버튼 클릭시 회원 삭제 완료됨





