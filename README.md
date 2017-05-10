Projeto TECINFO WEB
===================

PROJETO PRÁTICO EM MIGRAÇÃO DE SISTEMAS


Projeto Maven que gera um arquivo WAR em Java 1.8 que utiliza as especificações:

	JSF / EJB3 / JPA 

E utiliza o Arquillian (http://arquillian.org/) como framework de testes;

Utilize uma IDE que tenha plugins Maven e Git:

| IDEs | detalhes|
| Eclipse (https://www.eclipse.org/downloads/eclipse-packages/) | plugins Maven e Git : precisa instalar |
| Netbeans (https://netbeans.org/downloads/) | plugins Maven e Git já vem incorporado a IDE |
| IntelliJ IDEA (esse tem a verão community que é free) (https://www.jetbrains.com/idea/#chooseYourEdition) | plugins Maven e Git já vem incorporado a IDE] |

Eu utilizo o Eclipse, porém utilizo muito o prompt de comando, já o Netbeans tem uma integração melhor com o Maven. Não tenho muito o que falar do IntelliJ IDE pois não utilizo muito ele

Caso queira utilizar o prompt de comando, será necessário instalar o Maven (http://maven.apache.org/) que é uma ferramenta de gerenciamento de dependências e de contrução de artefatos.

É interessante saber como o Maven trabalha, dê uma olhada nos goals dele e em seu ciclo de construção no site do próprio Maven (http://maven.apache.org/) ou então procure no google algum artigo em português se preferir.

Para executar o projeto pela primeira vez pelo prompt:

O comando abaixo realiza os testes dos códigos, contrói o WAR e realiza a implantação em um servidor wildfly 10

```
$ mvn wildfly:run

```

Para limpar os binários:

```
$ mvn clean

```

Para executar somente os testes:

```
$ mvn test

```

Para executar a contrução do WAR:

```
$ mvn package

```

Caso queira ignorar os testes, basta adicionar a opção  : -DskipTests como baixo:

```
$ mvn package -DskipTests

```



