# REGISTRANT

Sistema para cadastro automático das informações de novos associados da Breadware.

## Apresentação

Atualmente o cadastro de informações de novos associados são realizados manualmente através do seguinte fluxo:

<p align="center">
<img alt="Processo atual de cadastro de novos associados" src="https://user-images.githubusercontent.com/13152452/77569171-87324080-6ea8-11ea-9acb-55c71467b177.png">
</p>

Através do fluxo, é possível perceber que:
- O processo depende de uma pessoa voluntária para ser realizado.
- Nem sempre a pessoa voluntária estará disponível. É necessário aguardá-la pra que o processo tenha continuidade.
- Existem muitas etapas manuais sujeitos a falha humana no processo.

O projeto existente neste repositório visa melhorar o processo de cadastro de novos associados substituindo a necessidade de intervenção de um voluntário por um sistema de cadastro automático atendendo o seguinte fluxo:

<p align="center">
<img alt="Processo desejado de cadastro de novos associados" src="https://user-images.githubusercontent.com/13152452/77569209-9a451080-6ea8-11ea-9a57-9e7cb5fe5334.png">
</p>

Com isto será possível reduzir a quantidade de etapas manuais necessárias para a conclusão do processo e também o tempo de resposta do cadastro ao novo associado.

Mais informações sobre o projeto podem ser obtidas no [card do projeto dentro do Trello da Praia de Porto Alegre](http://google.com).

## Tecnologias utilizadas

- Java 11 ou superior
- Apache Maven 3.6.3

**Observações:**
- O sistema foi testado utilizando Oracle JVM versão 13.0.2 2020-01-14.

## Instalando o ambiente de desenvolvimento

Algumas instruções abaixo referem-se a comandos a serem realizados no terminal por ser um ponto comum entre diversos sistemas operacionais. Fique a vontade em utilizar outro programa, mas esteja ciente de que resultados obtidos devem ser os mesmos. 

### 1. Instalar os softwares necessários
Antes de iniciar a instalação, certifique-se de que você possui instalado os programas indicados no item [tecnologias utilizadas](#tecnologias-utilizadas).

### 2. Clonar o repositório git

| Protocolo | Comando |
| --- | --- |
| HTTPS | `git clone https://github.com/breadware/registrant.git` |
| SSH | `git clone git@github.com:breadware/registrant.git` |

### 3. Criar ou solicitar as credenciais do programa no Google Cloud Platform

Para que o sistema possa trabalhar com os dados do usuário, o Google Cloud Platform (GCP) exige que o sistema solicite em tempo de execução uma autorização ao cliente. Para realizar esta solicitação, é necessário utilizar uma credenciação OAuth 2.0 para que o GCP reconheça o sistema solicitante.

**Observação**: Caso você seja apenas o contribuinte, solicite estas credenciais ao atual líder do projeto.

3.1. Acesse o console do [Google Cloud Platform](https://console.cloud.google.com) utilizando as credenciais da conta Google do projeto.

3.2. No canto superior esquerdo da página clique no item "Select a project" e então selecione o projeto "Registrant".

<p align="center">
<img width="400" alt="Localização do item &quot;Select a project&quot; na página." src="https://user-images.githubusercontent.com/13152452/77966063-b980db00-72b8-11ea-988c-ba356a247872.png">
</p>
<br/><br/>
<p align="center">
<img width="600" alt="Localização do projeto &quot;Registrant&quot; na lista de projetos." src="https://user-images.githubusercontent.com/13152452/77966067-bc7bcb80-72b8-11ea-963f-3b2a3af8100f.png">
</p>

3.3. Novamente no canto superior esquerdo da página, abra o menu de opções do console, selecione o menu "APIs & Services", item "Credentials".

<p align="center">
<img width="400" alt="Localização do menu de ações do console, o submenu &quot;APIs & Services&quot; e o item &quot;Credentials&quot;" src="https://user-images.githubusercontent.com/13152452/77966072-bf76bc00-72b8-11ea-8843-fae164d3ec98.png">
</p>

3.4. Na área central do console, clique no botão "Create credentials" e selecione a opção "OAuth Client ID".

<p align="center">
<img width="600" alt="Área central da tela de criação de credenciais apresentando o botão &quot;Create credentials&quot; e o item &quot;OAuth client ID&quot;." src="https://user-images.githubusercontent.com/13152452/77966088-c3a2d980-72b8-11ea-8aee-4f795a293543.png">
</p>

3.5. na tela "Create OAuth client ID", selecione a opção "other", preencha o campo "Name" com algo que identifique o programa (p. ex. "registrant") e clique em "Create".

<p align="center">
<img width="600" alt="Tela &quote;Create OAuth client ID&quot; apresentando o preenchimento dos campos &quot;Application type&quot;, &quot;Name&quot; e a localização do campo &quot;Create&quot;" src="https://user-images.githubusercontent.com/13152452/77966095-c6053380-72b8-11ea-8f29-cefaae29a3b4.png">
</p>

3.6. Na tela "OAuth client created" apenas clique em "Ok".

<p align="center">
<img width="400" alt="Tela &quote;OAuth client created&quot; apresentando a localização do botão &quot;Ok&quot;" src="https://user-images.githubusercontent.com/13152452/77966179-e03f1180-72b8-11ea-840a-c8e31c7010df.png">
</p>

3.7. Na área central da página, localize a nova chave criada e clique no botão de download das informações.

<p align="center">
<img alt="Localização do botão de download de informações da nova chave criada" src="https://user-images.githubusercontent.com/13152452/77966101-c8678d80-72b8-11ea-9eda-10e60e4ebdb2.png">
</p>

3.8. Salve o arquivo JSON em um diretório do seu computador **não utilize o diretório de fontes do projeto para não correr o risco de fazer o upload destes dados para o repositório público. Caso isto ocorra, será necessário invalidar a chave e criar uma nova.**.

### 4. Criar ou solicitar as credenciais de conta de serviços no Google Cloud Platform

**Observação**: Caso você seja apenas o contribuinte, solicite estas credenciais ao atual líder do projeto.

Para poder consumir alguns serviços do Google Cloud Platform (GCP), é necessário criar uma chave para identificar o programa que está realizando a solicitação.

4.1. Acesse o console do [Google Cloud Platform](https://console.cloud.google.com) utilizando as credenciais da conta Google do projeto.

4.2. No canto superior esquerdo da página clique no item "Select a project" e então selecione o projeto "Registrant".

<p align="center">
<img width="400" alt="Localização do item &quot;Select a project&quot; na página." src="https://user-images.githubusercontent.com/13152452/77966063-b980db00-72b8-11ea-988c-ba356a247872.png">
</p>
<br/><br/>
<p align="center">
<img width="600" alt="Localização do projeto &quot;Registrant&quot; na lista de projetos." src="https://user-images.githubusercontent.com/13152452/77966067-bc7bcb80-72b8-11ea-963f-3b2a3af8100f.png">
</p>

4.3. Novamente no canto superior esquerdo da página, abra o menu de opções do console, selecione o menu "APIs & Services", item "Credentials".

<p align="center">
<img width="400" alt="Localização do menu de ações do console, o submenu &quot;APIs & Services&quot; e o item &quot;Credentials&quot;" src="https://user-images.githubusercontent.com/13152452/77966072-bf76bc00-72b8-11ea-8843-fae164d3ec98.png">
</p>

4.4. Na área central do console, clique no botão "Create credentials" e selecione a opção "Service Account".

<p align="center">
<img width="600" alt="Localização do botão &quot;Create Credentials&quot; e do item &quot;Service Account&quot; na área central do console" src="https://user-images.githubusercontent.com/13152452/77966133-d1f0f580-72b8-11ea-9801-47df99768f86.png">
</p>

4.5. No campo o nome da conta no campo "Service Account Name" e deixe o campo "Service account ID" com o seu valor padrão. Caso julgue necessário, preencha o campo "Service account description". Clique em "Create" para criar a nova credencial.

<p align="center">
<img width="600" alt="Exemplo de preenchimento dos campos &quot;Service Account Name&quot; e &quot;Service Account Description&quot;" src="https://user-images.githubusercontent.com/13152452/77966145-d4534f80-72b8-11ea-8a4b-c178db6aa002.png">
</p>

4.6. Na tela de autorizações, campo "Select a role", informe as seguintes roles:
- Pub/sub subscriber

<p align="center">
<img width="600" alt="Localização da role &quot;Pub/Sub subscriber&quot; e de preenchimento do campo &quot;Select a role&quot;" src="https://user-images.githubusercontent.com/13152452/77966151-d6b5a980-72b8-11ea-8073-904140fdfe42.png">
</p>

Clique em "Continue" para continuar a criação da credencial.

<p align="center">
<img width="600" alt="Localização do botão &quot;Continue&quot; na tela de criação de nova credencial." src="https://user-images.githubusercontent.com/13152452/77968623-2ea2df00-72be-11ea-87e5-5a01a4915320.png">
</p>

4.7. Na tela "Grant users access to this service account" clique no botão "Create key".

<p align="center">
<img width="600" alt="Localização do botão &quot;Create Key&quot; na tela de criação da nova credencial." src="https://user-images.githubusercontent.com/13152452/77968626-306ca280-72be-11ea-8a09-297088501474.png">
</p>

4.8. Na área "Create key (optional)", deixe marcada a opção JSON para o campo "Key type" e clique no botão "Create".

<p align="center">
<img width="600" alt="Tela &quot;Create key (optional)&quot; com a opção &quot;JSON&quot; marcada e a localização do botão &quot;Create&quot;." src="https://user-images.githubusercontent.com/13152452/77968632-32cefc80-72be-11ea-86f8-a36c55ff6aa1.png">
</p>

4.9. Uma vez concluído, o GCP console lhe enviará a chave privada utilizada para a autenticação. Salve este arquivo em um diretório do seu computador **não utilize o diretório de fontes do projeto para não correr o risco de fazer o upload destes dados para o repositório público. Caso isto ocorra, será necessário invalidar a chave e criar uma nova.**.

### 5. Importar o projeto no IntelliJ IDE

**Observação**: Não há problema nenhum em trabalhar em outro IDE como, por exemplo, o Eclipse. Apenas **cuide para não carregar para o projeto arquivos de configuração específicos**. Caso tenha dúvidas sobre como evitar isto entre em contato com o líder do projeto ou faça uma pesquisa no Google. 🙂

5.1. Na tela inicial do IntelliJ, selecione a opção "Import project".

<p align="center">
<img width="600" alt="Tela Inicial do IntelliJ com a opção &quot;Import Project&quot; selecionada." src="https://user-images.githubusercontent.com/13152452/77968849-b983d980-72be-11ea-97fd-0f81fcd2e523.png">
</p>

5.2. Selecione o diretório onde o projeto Git foi clonado e clique em "Open".

<p align="center">
<img width="600" alt="Exemplo de localização do diretório que contém o projeto Git clonado." src="https://user-images.githubusercontent.com/13152452/77968861-bbe63380-72be-11ea-8334-2d210a031305.png">
</p>

5.3. Na tela "Import Project" selecione a opção "Maven" e clique em "Finish".

<p align="center">
<img width="600" alt="Tela &quot;Import Project&quot; com a opção &quot;Maven&quot; selecionada." src="https://user-images.githubusercontent.com/13152452/77968868-be488d80-72be-11ea-9880-2a74a114cdfd.png">
</p>

### 6. Criar a configuração de execução do sistema

Com o projeto aberto no IntelliJ, abra o menu `Run` e selecione a opção `Edit Configurations...`.

<p align="center">
<img width="400" alt="Tela &quot;Import Project&quot; com a opção &quot;Maven&quot; selecionada." src="https://user-images.githubusercontent.com/13152452/78395192-6dd57680-75c3-11ea-9e42-dd2d3a175502.png">
</p>

Na tela `Run/Debug Configurations`, clique no ícone `+` para criar uma nova configuração e selecione a opção `Application`.

<p align="center">
<img width="600" alt="Tela &quot;Run/Debug Configurations&quot; com a opção &quot;Application&quot; selecionada." src="https://user-images.githubusercontent.com/13152452/78395220-74fc8480-75c3-11ea-80f7-3db1fb002d27.png">
</p>

No campo `Name`, informe o nome da configuração (p. ex. Registrant).

No campo `Main class` informe `br.com.breadware.Registrant`.

<p align="center">
<img width="600" alt="Tela &quot;Run/Debug Configurations&quot; com um exemplo de preenchimento dos campos &quot;Name&quot; e &quot;Main class&quot;." src="https://user-images.githubusercontent.com/13152452/78395221-75951b00-75c3-11ea-8b83-8a87e83479c3.png">
</p>

No campo `Environment variables`, crie as variáveis de ambientes necessárias conforme indicado no [item 7](#7-definir-variáveis-de-ambiente). Utilize o botão `Browse` no final do campo para facilitar a visualização e preenchimento.

<p align="center">
<img width="600" alt="Tela &quot;Environment Variables&quot; com um exemplo de preenchimento das variáveis de ambiente." src="https://user-images.githubusercontent.com/13152452/78395223-76c64800-75c3-11ea-8c2a-21c561a7a9bc.png">
</p>

Clique em `Ok` para salvar a nova configuração.

Na tela principal do IntelliJ, abra o menu de seleção de configuração de execução e selecione a nova configuração criada.

<p align="center">
<img width="600" alt="Localização do menu de seleção de configuração de execução na tela principal do IntelliJ." src="https://user-images.githubusercontent.com/13152452/78723491-e446e100-7901-11ea-991f-22ba373953be.png">
</p>  

Clique no botão ▶️ para executar o programa.

### 7. Definir variáveis de ambiente

As variáveis de ambiente são utilizadas para definir propriedades particulares de um ambiente de execução ou de informações sensíveis que não podem ficar expostas no servidor de controle de código. O sistema atualmente conta com a utilização das seguintes variáveis de ambiente:

- GOOGLE_APPLICATION_CREDENTIALS
- GOOGLE_CLIENT_ID

7.1 A propriedade `GOOGLE_APPLICATION_CREDENTIALS` é utilizada pelas bibliotecas de API da Google para localizar o arquivo que contém as credenciais de conta de serviços criadas no [item 4](#4-criar-ou-solicitar-as-credenciais-de-conta-de-serviços-no-google-cloud-platform).

`GOOGLE_APPLICATION_CREDENTIALS=/Users/marceloleite2604/Documents/Breadware/Files/service-account-key.json`

7.2 A propriedade `GOOGLE_CLIEND_ID` é utilizada pelo programa para localizar o arquivo criado no [item 3](#3-criar-ou-solicitar-as-credenciais-do-programa-no-google-cloud-platform) que contém as credenciais as informações de identificação o cliente ao solicitar a chave de autorização para o servidor OAuth2.

`GOOGLE_CLIEND_ID=/Users/marceloleite2604/Documents/Breadware/Files/client-id.json`