# REGISTRANT

Sistema para cadastro automático das informações de novos associados da Breadware.

## Apresentação

Atualmente, o cadastro de informações de novos associados são realizados manualmente através do seguinte fluxo:

![Imagem apresentando o processo atual de cadastro de novos associados](https://user-images.githubusercontent.com/13152452/77569171-87324080-6ea8-11ea-9acb-55c71467b177.png)

Através do fluxo, é possível perceber que:
- O processo depende de uma pessoa voluntária para ser realizado.
- Nem sempre a pessoa voluntária estará disponível. É necessário aguardá-la pra que o processo tenha continuidade.
- Existem muitas etapas manuais sujeitos a falha humana no processo.

O projeto existente neste repositório visa melhorar o processo de cadastro de novos associados substituindo a necessidade de intervenção de um voluntário por um sistema de cadastro automático atendendo o seguinte fluxo:

![Imagem apresentando o processo desejado de cadastro de novos associados](https://user-images.githubusercontent.com/13152452/77569209-9a451080-6ea8-11ea-9a57-9e7cb5fe5334.png)

Com isto, será possível reduzir a quantidade de etapas manuais necessárias para a conclusão do processo e também o tempo de resposta do cadastro ao novo associado.

Mais informações sobre o projeto podem ser obtidas no [card do projeto dentro do Trello da Praia de Porto Alegre](http://google.com).

## Tecnologias utilizadas

- Java 11 ou superior
- Apache Maven 3.6.3

**Observações:**
- O sistema foi testado utilizando Oracle JVM versão 13.0.2 2020-01-14.

## Instalando o ambiente de desenvolvimento

Algumas instruções abaixo referem-se a comandos a serem realizados no terminal por ser um ponto comum entre diversos sistemas operacionais. Fique a vontate em utilizar outro programa, mas esteja ciente de que resultados obtidos devem ser os mesmos. 

### 1. Instalar os softwares necessários
Antes de iniciar a instalação, certifique-se de que você possui instalado os programas indicados no item [tecnologias utilizadas](#tecnologias-utilizadas).

### 2. Clonar o repositório git

| Protocolo | Comando |
| --- | --- |
| HTTPS | `git clone https://github.com/breadware/registrant.git` |
| SSH | `git clone git@github.com:breadware/registrant.git` |

### 3. Criar ou solicitar as credenciais do programa no Google Cloud Platform

**Observação**: Caso você seja apenas o contribuinte, solicite estas credenciais ao atual líder do projeto.

Para que o sistema possa trabalhar com os dados do usuário, o Google Cloud Platform (GCP) exige que o sistema solicite em tempo de execução uma autorização ao cliente. Para realizar esta solicitação, é necessário utilizar uma credenciação OAuth 2.0 para que o GCP reconheça o sistema solicitante.

3.1. Acesse o console do [Google Cloud Platform](https://console.cloud.google.com) utilizando as credenciais da conta Google do projeto.

3.2. No canto superior esquerdo da página clique no item "Select a project" e então selecione o projeto "Registrant".

3.3. Novamente no canto superior esquerdo da página, abra o menu de opções do console, selecione o menu "APIs & Services", item "Credentials".

3.4. Na área central do console, clique no botão "Create credentials" e selecione a opção "OAuth Client ID".

3.5. na tela "Create OAuth client ID", selecione a opção "other", preencha o campo "Name" com algo que identifique o programa (p. ex. "registrant") e clique em "Create".

3.6. Na tela "OAuth client created" apenas clique em "Ok".

3.7. Na área central da página, localize a nova chave criada e clique no botão de download das informações.

3.8. Salve o arquivo JSON em um diretório do seu computador **não utilize o diretório de fontes do projeto para não correr o risco de fazer o upload destes dados para o repositório público. Caso isto ocorra, será necessário invalidar a chave e criar uma nova.**.

[**TODO: Descrever como associar o arquivo de credenciais ao projeto após resolver a [issue #3](../../issues/3)**]

### 4. Criar ou solicitar as credenciais de conta de serviços no Google Cloud Platform

**Observação**: Caso você seja apenas o contribuinte, solicite estas credenciais ao atual líder do projeto.

Para poder consumir alguns serviços do Google Cloud Platform (GCP), é necessário criar uma chave para identificar o programa que está realizando a solicitação.

4.1. Acesse o console do [Google Cloud Platform](https://console.cloud.google.com) utilizando as credenciais da conta Google do projeto.

4.2. No canto superior esquerdo da página clique no item "Select a project" e então selecione o projeto "Registrant".

4.3. Novamente no canto superior esquerdo da página, abra o menu de opções do console, selecione o menu "APIs & Services", item "Credentials".

4.4. Na área central do console, clique no botão "Create credentials" e selecione a opção "Service Account".

4.5. No campo o nome da conta no campo "Service Account Name" e deixe o campo "Service account ID" com o seu valor padrão. Caso julgue necessário, preencha o campo "Service account description". Clique em "Create" para criar a nova credencial.

4.6. Na tela de autorizações, campo "Select a role", informe as seguintes roles:
- Pub/sub subscriber

Clique em "Continue" para continuar a criação da credencial.

4.7. Na tela "Grant users access to this service account" clique no botão "Create key".

4.8. Na área "Create key (optional)", deixe marcada a opção JSON para o campo "Key type" e clique no botão "Create".

4.9. Uma vez concluído, o GCP console lhe enviará a chave privada utilizada para a autenticação. Salve este arquivo em um diretório do seu computador **não utilize o diretório de fontes do projeto para não correr o risco de fazer o upload destes dados para o repositório público. Caso isto ocorra, será necessário invalidar a chave e criar uma nova.**.

### 5. Definir variáveis de ambiente

[**TODO: Descrever como associar o arquivo de credenciais ao projeto após resolver a [issue #3](../../issues/3)**]

### 6. Importar o projeto no IntelliJ IDE

**Observação**: Não há problema nenhum em trabalhar em outro IDE como, por exemplo, o Eclipse. Apenas **cuide para não carregar para o projeto arquivos de configuração específicos**. Caso tenha dúvidas sobre como evitar isto entre em contato com o líder do projeto ou faça uma pesquisa no Google. 🙂

6.1. Na tela inicial do IntelliJ, selecione a opção "Import project"

6.2. Selecione o diretório onde o projeto Git foi clonado e clique em "Open".

6.3. Na tela "Import Project" selecione a opção "Maven" e clique em "Finish".
