Prova, Processo Seletivo Mais Vida
=========
Foi desenvolvido um CRUD simples, de acordo com o diagrama a baixo.
![](https://image.ibb.co/cFRCMx/model.png)

Requisitos
==========
* [Docker](https://docs.docker.com/install/)
* [Docker Compose](https://docs.docker.com/compose/install/)
* [Node](https://nodejs.org/en/download/)
* [Ionic 3](https://ionicframework.com/getting-started/)
* [Java 8](http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html)
* [Gradle](https://gradle.org/) ou [Maven](https://maven.apache.org/)
* (Sugestão de IDE) [IntelliJ IDEA](https://www.jetbrains.com/idea/)
* Certifique-se que as portas __27017__, __8080__ e 8100 estejam liberadas


Execução
==============
Para executar o projeto, é muito simples:

1. Primeiro, clone o repositório, e entre nele:
```R
git clone https://github.com/natons/exam-maisvida.git
```

```R
cd exam-maisvida/
```

2. Agora, baixe as imagens do [tomcat](https://hub.docker.com/_/tomcat/) e do [mongo](https://hub.docker.com/_/mongo/), da seguinte forma:
```R
docker pull tomcat
```

```R
docker pull mongo
```
3. Agora, execute o compose. Dessa forma, a api REST e o DB já estarão em funcionamento.
```R
docker-compose up -d
```
Caso queira visualizar o log, use o seguinte comando:
```R
docker-compose up
```
4. Na mesma pasta, entre no sub diretório __exam-maisvida__, que pertence ao aplicativo. Digite os seguintes comandos:
```R
npm install
```
```R
ionic serve -l
```
5. Pronto, a aplicação já estará funcionando no endereço: __http://localhost:8100/ionic-lab__
6. As credenciais para se logar no app são: usuário __roy__ senha __spring__
7. A API, está funcionando no endereço __http://localhost:8080__
8. A documentação da API, pode ser acessada pelo endereço: __http://localhost:8080/swagger-ui.html__

Novo Build
================
Caso deseje gerar um novo build do aplicativo, basta alterá-lo que seu servidor ira detectar as modificações. Porém,
no caso da API, é necessário seguir os seguintes procedimentos:
1. Recomendo que use a IDE supracitada. Dessa forma, importe o arquivo __build.gradle__ ou __pom.xml__.
2. Faça as modificações, e gere um build com um artefato de extensão __WAR__.
3. Copie o artefato para o diretório __server/artifacts__ com o nome __ROOT.war__ (isso serve para a api rodar no endereço __http://localhost:8080__).
4. Pronto, as alterações serão efetuadas.

[Perfil](https://github.com/natons)
