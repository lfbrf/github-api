# github-api
Api Github em Java

### Disponível em
https://githubapij.herokuapp.com

### Funcionalidade
Manipulação da API do Github, listagem de repositórios, salvar e deletar repositório.

### Tecnologias Utilizadas
Java, Spring, HTML, CSS, Hibernate, Maven, MYsql

### Observação
Para rodar o projeto localmente
#### Clonar repositório

#### Instalar as dependêcnias do projeto com o comando:
mvn clean install na pasta do projeto
#### Adicionar um novo token 
Na classe htttp onde o mesmo é usado (variável token e url).
#### No github é possvel gerar em:
Settings -> Developer Settings -> Personal Access Tokens

#### Alterar informações de conexão com o banco

No arquivo applications properties (/src/main/resources/application.properties)

Alterar usuário (spring.datasource.username)  e senha (spring.datasource.password) de acesso ao banco.

#### Observação

Se necessário pode alterar o banco também. Criar o database no banco (por padrão é o githubapi). A tabela é criada ao levantar a aplicação. 

