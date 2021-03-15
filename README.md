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
<h1>회원단</h1>

<p>1. 로그인 기능</p>

![image](https://user-images.githubusercontent.com/73808497/111083195-9c87fb00-854f-11eb-857c-bef197b541cd.png)
![image](https://user-images.githubusercontent.com/73808497/111083245-e40e8700-854f-11eb-9b7f-c24cb78d9c27.png)

세션으로 로그인 정보를 보내어, 로그인이 성공된다. 로그인시, 로그아웃 태그 생성

<p>2. 아이디/비밀번호 식별 기능</p>

![image](https://user-images.githubusercontent.com/73808497/111083607-b88c9c00-8551-11eb-8c29-83f382774f0e.png)
회원 가입이 되지 않은 아이디/비밀번호 입력시
![image](https://user-images.githubusercontent.com/73808497/111083629-ca6e3f00-8551-11eb-9d21-041eb41bbc82.png)
자바스크립트의 alert 메서드를 이용한 알림

<p>3. 회원 가입 기능</p>

![image](https://user-images.githubusercontent.com/73808497/111083351-8169bb00-8550-11eb-85a7-6b18a868320e.png)
![image](https://user-images.githubusercontent.com/73808497/111083485-40be7180-8551-11eb-862e-c825c7632168.png)
![image](https://user-images.githubusercontent.com/73808497/111083508-4fa52400-8551-11eb-80cc-3bae4d6993b3.png)

회원 가입 태그 클릭시 회원 가입 폼 생성 및 가입하기 클릭시 회원 가입 완료

<p>4. 회원 삭제 기능</p>

![image](https://user-images.githubusercontent.com/73808497/111143632-5e2f2200-85c9-11eb-9359-ad4286f27f71.png)
![image](https://user-images.githubusercontent.com/73808497/111143649-66875d00-85c9-11eb-970a-2440f1b23d9a.png)

회원 리스트에서 삭제 버튼 클릭시 회원 삭제 완료

<hr contenteditable="false" data-ke-type="horizontalRule" data-ke-style="style5" />

<h1>게시판 기능</h1>

<p>1. 글쓰기 기능</p>

![image](https://user-images.githubusercontent.com/73808497/111144024-dc8bc400-85c9-11eb-8caf-943274b7d83f.png)

listArticle.jsp의 글쓰기 태그를 클릭하여 컨트롤러에 articleForm.do 메서드 요청한다

![image](https://user-images.githubusercontent.com/73808497/111144210-1fe63280-85ca-11eb-9bd1-658646154351.png)

articleForm.jsp 화면 모습. 글쓰기 폼과, 이미지 첨부 기능 가능

![image](https://user-images.githubusercontent.com/73808497/111144331-499f5980-85ca-11eb-9d75-17c8b0cc4fae.png)

![image](https://user-images.githubusercontent.com/73808497/111144401-6045b080-85ca-11eb-8387-1e38157b27e3.png)

![image](https://user-images.githubusercontent.com/73808497/111144623-a3a01f00-85ca-11eb-9337-7332688bc8d7.png)

addNewArticle.do 요청을 컨트롤러에 요청하여 정상적으로 글이 등록된 모습


<p>2. 글 수정 기능</p>

![image](https://user-images.githubusercontent.com/73808497/111144820-ec57d800-85ca-11eb-95b4-61004308f525.png)

수정하기 버튼을 눌러 글 수정 가능

![image](https://user-images.githubusercontent.com/73808497/111145121-3e98f900-85cb-11eb-8b14-3a5282fcce3a.png)

제목, 글 내용, 첨부파일 수정 가능하다. 수정반영하기 버튼을 눌러 수정 완료

![image](https://user-images.githubusercontent.com/73808497/111145240-62f4d580-85cb-11eb-9434-74b4d93fef8b.png)

![image](https://user-images.githubusercontent.com/73808497/111145570-bebf5e80-85cb-11eb-84a6-4dfac1dc9614.png)

수정 완료된 모습 확인


<p>3. 작성자 확인하여 수정 및 삭제 권한 부여<p>

![image](https://user-images.githubusercontent.com/73808497/111145855-178ef700-85cc-11eb-81f8-9326e60d7314.png)

다른 계정으로 접속하여 글 클릭시, 본인이 작성한 글만 수정 권한 부여한다. 즉 작성자를 확인하여 수정하기 버튼과 삭제 버튼이 뜨지 않는 모습을 볼 수 있다


<p>4. 글 삭제 기능<p>

![image](https://user-images.githubusercontent.com/73808497/111146259-8ff5b800-85cc-11eb-9ec6-75a04c837f13.png)

삭제하기 버튼을 클릭하여 removeArticle.do 메서드를 컨트롤러에서 호출한다. 

![image](https://user-images.githubusercontent.com/73808497/111146370-aef44a00-85cc-11eb-8b5d-2c54c147cda9.png)
![image](https://user-images.githubusercontent.com/73808497/111146426-c16e8380-85cc-11eb-8e32-688ad6f312bb.png)

삭제 완료 알림이 뜨며 글이 삭제되었음을 확인할 수 있다.












