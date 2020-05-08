# REGISTRANT

Programa para cadastro automático das informações de novos associados da Breadware.

## Índice

[1. Apresentação][1]

[2. Tecnologias utilizadas][2]

[3. Como instalar o ambiente de desenvolvimento][3]

&nbsp;&nbsp;&nbsp;&nbsp;[3.1. Instalar os softwares necessários][3.1]

&nbsp;&nbsp;&nbsp;&nbsp;[3.2. Clonar o repositório Git][3.2]

&nbsp;&nbsp;&nbsp;&nbsp;[3.3. Criar ou solicitar as credenciais do programa no Google Cloud Platform][3.3]

&nbsp;&nbsp;&nbsp;&nbsp;[3.4. Criar ou solicitar as credenciais de conta de serviços no Google Cloud Platform][3.4]

&nbsp;&nbsp;&nbsp;&nbsp;[3.5. Importar o projeto no IntelliJ IDE][3.5]

&nbsp;&nbsp;&nbsp;&nbsp;[3.6. Criar a configuração de execução do programa][3.6]

&nbsp;&nbsp;&nbsp;&nbsp;[3.7. Definir variáveis de ambiente][3.7]

[4. Como executar o programa][4]

&nbsp;&nbsp;&nbsp;&nbsp;[Executando o programa via IntelliJ][4.1]

&nbsp;&nbsp;&nbsp;&nbsp;[Executando o programa via linha de comando][4.2]

&nbsp;&nbsp;&nbsp;&nbsp;[Executando o programa através do Google App Engine][4.3]

## 1. Apresentação

Atualmente o cadastro de informações de novos associados são realizados manualmente através do seguinte fluxo:

<p align="center">
<img alt="Processo atual de cadastro de novos associados" src="https://user-images.githubusercontent.com/13152452/77569171-87324080-6ea8-11ea-9acb-55c71467b177.png">
</p>

Através do fluxo, é possível perceber que:
- O processo depende de uma pessoa voluntária para ser realizado.
- Nem sempre a pessoa voluntária estará disponível. É necessário aguardá-la pra que o processo tenha continuidade.
- Existem muitas etapas manuais sujeitas a falha humana no processo.

O projeto existente neste repositório visa melhorar o processo de ingresso de novos associados substituindo a necessidade de intervenção de um voluntário por um programa de cadastro automático atendendo o seguinte fluxo:

<p align="center">
<img alt="Processo desejado de cadastro de novos associados" src="https://user-images.githubusercontent.com/13152452/77569209-9a451080-6ea8-11ea-9a57-9e7cb5fe5334.png">
</p>

Com isto será possível reduzir a quantidade de etapas manuais necessárias para a conclusão do processo e também o tempo de resposta do cadastro ao novo associado.

Mais informações sobre o projeto podem ser obtidas no [card do projeto dentro do Trello da Praia de Porto Alegre](http://google.com).

[1]: #1-apresentação
[2]: ./docs/tecnologias-e-instalacao.md#2-tecnologias-utilizadas
[2.1]: ./docs/tecnologias-e-instalacao.md#21-tecnologias-necessrias-no-ambiente-de-execução
[2.2]: ./docs/tecnologias-e-instalacao.md#22-tecnologias-utilizadas-como-solução-no-programa
[3]: ./docs/tecnologias-e-instalacao.md#3-como-instalar-o-ambiente-de-desenvolvimento
[3.1]: ./docs/tecnologias-e-instalacao.md#31-instalar-os-softwares-necessários
[3.2]: ./docs/tecnologias-e-instalacao.md#32-clonar-o-repositório-git
[3.3]: ./docs/tecnologias-e-instalacao.md#33-criar-ou-solicitar-as-credenciais-do-programa-no-google-cloud-platform
[3.4]: ./docs/tecnologias-e-instalacao.md#34-criar-ou-solicitar-as-credenciais-de-conta-de-serviços-no-google-cloud-platform
[3.5]: ./docs/tecnologias-e-instalacao.md#35-importar-o-projeto-no-intellij-ide
[3.6]: ./docs/tecnologias-e-instalacao.md#36-criar-a-configuração-de-execução-do-programa
[3.7]: ./docs/tecnologias-e-instalacao.md#37-definir-variáveis-de-ambiente
[4]: ./docs/como-executar-o-programa.md#4-como-executar-o-programa
[4.1]: ./docs/como-executar-o-programa.md#41-executando-o-programa-via-intellij
[4.2]: ./docs/como-executar-o-programa.md#42-executando-o-programa-via-linha-de-comando
[4.3]: ./docs/como-executar-o-programa.md#43-executando-o-programa-através-do-google-app-engine