# brewer

##### Como executar o projeto no ambiente local do desenvolvedor, usando o Apache Tomcat v9.0.26
1 - Instalar o [docker](https://www.docker.com/) no computador

2 - Instalar a imagem do [postrges](https://www.postgresql.org/) dentro do docker e já criar a base de dados __brewer__. Execute a linha abaixo no terminal:

    docker run --name pg-brewer -e POSTGRES_USER=brewer -e POSTGRES_PASSWORD=brewer -d -p 5432:5432 postgres

3 - Executar as migrações com o [flyway](https://flywaydb.org/) no banco de dados 

    mvn flyway:migrate -Dflyway.url=jdbc:postgresql://localhost:5432/brewer -Dflyway.user=brewer -Dflyway.password=brewer

4 - Baixar o servidor (apache tomcat)[http://tomcat.apache.org/] e descompactar em uma pasta de sua preferência
 
5 - Incluir as dependências abaixo na pasta _<localizacao_do_tomcat>_/lib

* [c3p0-0.9.5.2.jar](https://repo1.maven.org/maven2/com/mchange/c3p0/0.9.5.2/c3p0-0.9.5.2.jar)
* [mchange-commons-java-0.2.11.jar](https://repo1.maven.org/maven2/com/mchange/mchange-commons-java/0.2.11/mchange-commons-java-0.2.11.jar)
* [postgresql-42.2.5.jar](https://github.com/pgjdbc/pgjdbc/archive/REL42.2.5.zip)

6 - Gerar o pacote (__war__) e incluir na pasta _<localizacao_do_tomcat>_/webapps

7 - Criar o arquivo __setenv.sh__ (unix) ou __setenv.bat__ (windows) na pasta _<localizacao_do_tomcat>_/bin com o conteúdo abaixo
    
    export AWS_ACCESS_KEY_ID = *seu_key_id_amazon
    export AWS_SECRET_ACCESS_KEY = *seu_access_key_amazon

8 - Executar o comando __./startup.sh__ (unix) ou __startup.bat__ (windows) dentro da pasta _<localizacao_do_tomcat>_/bin para iniciar o tomcat

##### Outros
Para que as integrações da aplicação funcione corretamente é necessário criar as variáveis abaixo:

EMAIL:
email.from={nome@email.com.br}
email.password={senha}

AWS:
AWS_ACCESS_KEY_ID={keyid}
AWS_SECRET_ACCESS_KEY={accesskey}
spring.profiles.active=local ou prod
