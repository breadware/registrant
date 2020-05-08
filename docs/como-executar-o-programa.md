# 4. Como executar o programa
A forma de executar o programa depende do ambiente e ferramenta utilizada.
 
## 4.1. Executando o programa via IntelliJ
Para executar o programa através da IDE IntelliJ, basta seguir os passos da [instalação do ambiente de desenvolvimento][3], em especial os itens [importar o projeto no IntelliJ IDE][3.5] e [criar a configuração de execução do programa][3.6].

## 4.2. Executando o programa via linha de comando

4.2.1. Certifique-se que de que o ambiente possui todos os softwares necessários descritos no [item 2.1][2.1].

4.2.2. Siga os passos descritos no [item 3.2][3.2] para clonar o conteúdo do repositório no ambiente que será utilizado para a execução.

4.2.3. Siga os passos descritos no [item 3.3][3.3] e [3.4][3.4] para obter os arquivos de credenciais utilizados pelo programa.

4.2.4. Siga os passos descritos no [item 3.7][3.7] para criar no ambiente as variáveis necessárias para a execução.

4.2.5. Dentro do diretório raiz do projeto utilize o Maven para para gerar um JAR executável do programa através da seguinte linha de comando.

`mvn clean package`

Ao final da execução, o Maven irá gerar dentro do diretório `target` um arquivo `registrant-<VERSAO>.jar`, onde `<VERSAO>` é a versão atual do projeto, por exemplo: `registrant-1.0.jar`.

4.2.6. Em seguida, basta executar o comando abaixo.

`java -jar ./target/registrant-<VERSAO>.jar`

Não esquecendo de substituir `<VERSAO>` pela versão atual do projeto.

## 4.3. Executando o programa através do Google App Engine

**Importante: O free-tier do Google App Engine permite que o programa seja executado até oito horas por dia. A partir disso a sua execução é cobrada. Logo, certifique-se de encerrar a sua execução através do Google Cloud Platform Console antes deste tempo ou quando ela não for mais necessária.** 

4.3.1. Para realizar o deploy do programa no Google App Engine, é necessário instalar no ambiente o [Google Cloud SDK](https://cloud.google.com/sdk).

4.3.2. Utilize o seguinte comando para identificar-se no Google Cloud Platform.

`gcloud auth login`

**Observação**: A conta utilizada no login deve ter autorização para realizar deploy de aplicações no projeto do Google Cloud Platform em uso.

4.3.3. Dentro do diretório raiz do projeto utilize o Maven para para gerar um JAR executável do programa através da seguinte linha de comando.

`mvn clean package`

4.3.4. Em seguida, execute o comando abaixo para fazer o deploy no Google App Engine.

`mvn -P appengine appengine:deploy`

**Observação**: 
Caso o deploy apresente o seguinte erro: 

```
INFO] GCLOUD: ERROR: (gcloud.app.deploy) Your deployment has succeeded, but promoting the new version to default failed. You may not have permissions to change traffic splits. Changing traffic splits requires the Owner, Editor, App Engine Admin, or App Engine Service Admin role. Please contact your project owner and use the `gcloud app services set-traffic --splits <version>=1` command to redirect traffic to your newly deployed version.
[INFO] GCLOUD:
[INFO] GCLOUD: Original error: INVALID_ARGUMENT: Invalid request.
[INFO] GCLOUD: - '@type': type.googleapis.com/google.rpc.BadRequest
[INFO] GCLOUD:   fieldViolations:
[INFO] GCLOUD:   - description: Traffic cannot be allocated to stopped version registrant. Please
[INFO] GCLOUD:       ensure that traffic is only allocated to serving versions.
[INFO] GCLOUD:     field: service.split.allocations[registrant]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  02:08 min
[INFO] Finished at: 2020-05-08T10:28:38-03:00
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal com.google.cloud.tools:appengine-maven-plugin:2.2.0:deploy (default-cli) on project registrant: App Engine application deployment failed: com.google.cloud.tools.appengine.operations.cloudsdk.process.ProcessHandlerException: com.google.cloud.tools.appengine.AppEngineException: Non zero exit: 1 -> [Help 1]
[ERROR]
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR]
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoExecutionException
```

Acesse o GCP Console, menu `App Engine`, item `Versions`.

<p align="center">
<img width="400" alt="Menu lateral do Google Cloud Platform apresentando os itens &quot;App Engine&quot; e &quot;Versions&quot; em destaque." src="https://user-images.githubusercontent.com/13152452/81411928-70267580-9119-11ea-8a02-3605c5cf3b53.png">
</p>

Verifique se a versão atual está parada. Caso positivo, o deploy retorna o erro acima informando que não foi possível redirecionar o tráfego para a nova versão.

<p align="center">
<img width="600" alt="Informações da versão atual do programa apresentando que ela está parada." src="https://user-images.githubusercontent.com/13152452/81412077-ae239980-9119-11ea-9684-821ba8780786.png">
</p>

Selecione o checkbox ao lado da versão e clique em `Start` para rodar a aplicação.

<p align="center">
<img width="600" alt="Informações da versão atual com o checkbox de seleção marcado e o botão &quot;start&quot; destacado." src="https://user-images.githubusercontent.com/13152452/81412329-14a8b780-911a-11ea-9f72-f8e279d68be9.png">
</p>

Confirme a inicialização clicando em `Start version`.

<p align="center">
<img width="400" alt="Mensagem de confirmação da inicialização do serviço com a opção &quot;start version&quot; destacada." src="https://user-images.githubusercontent.com/13152452/81412536-63565180-911a-11ea-8a94-f94d9a6511b3.png">
</p>

Aguarde até que o status da versão seja alterado para `serving` e tente realizar o comando de deploy novamente.

<p align="center">
<img width="600" alt="Informações da versão atual do programa apresentando que ela está em execução." src="https://user-images.githubusercontent.com/13152452/81412712-a44e6600-911a-11ea-8dfb-0a94ce588d3e.png">
</p>


[1]: ../REAME.md#1-apresentação
[2]: ./tecnologias-e-instalacao.md#2-tecnologias-utilizadas
[2.1]: ./tecnologias-e-instalacao.md#21-tecnologias-necessárias-no-ambiente-de-execução
[2.2]: ./tecnologias-e-instalacao.md#22-tecnologias-utilizadas-como-solução-no-programa
[3]: ./tecnologias-e-instalacao.md#3-como-instalar-o-ambiente-de-desenvolvimento
[3.1]: ./tecnologias-e-instalacao.md#31-instalar-os-softwares-necessários
[3.2]: ./tecnologias-e-instalacao.md#32-clonar-o-repositório-git
[3.3]: ./tecnologias-e-instalacao.md#33-criar-ou-solicitar-as-credenciais-do-programa-no-google-cloud-platform
[3.4]: ./tecnologias-e-instalacao.md#34-criar-ou-solicitar-as-credenciais-de-conta-de-serviços-no-google-cloud-platform
[3.5]: ./tecnologias-e-instalacao.md#35-importar-o-projeto-no-intellij-ide
[3.6]: ./tecnologias-e-instalacao.md#36-criar-a-configuração-de-execução-do-programa
[3.7]: ./tecnologias-e-instalacao.md#37-definir-variáveis-de-ambiente
[4]: #4-como-executar-o-programa
[4.1]: #41-executando-o-programa-via-intellij
[4.2]: #42-executando-o-programa-via-linha-de-comando
[4.3]: #43-executando-o-programa-através-do-google-app-engine
[5]: ./como-contribuir-com-o-projeto.md#5-como-contribuir-com-o-projeto
[5.1]: ./como-contribuir-com-o-projeto.md#51-selecionando-uma-issue-para-trabalhar
[5.2]: ./como-contribuir-com-o-projeto.md#52-implementando-a-issue-no-ambiente-de-desenvolvimento
[5.3]: ./como-contribuir-com-o-projeto.md#53-encaminhando-a-sua-implementação-para-revisão