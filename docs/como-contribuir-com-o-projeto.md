# 5. Como contribuir com o projeto

Obrigado pelo interesse em colaborar com o projeto. Todos os pontos de melhoria estão mapeados nas [issues](../issues) do repositório no GitHub. 
**Observação**: Caso não exista nenhuma issue aberta, fale com o responsável do projeto para saber como contribuir.

*imagem 1*
<p align="center">
<img alt="" src="">
</p>

## 5.1. Selecionando uma issue para trabalhar
 
5.1.1. Escolha uma issue no qual deseja trabalhar e clique no seu nome.

*imagem 2*
<p align="center">
<img alt="" src="">
</p>

5.1.2. Na área lateral direita, inclua o seu nome na lista de assignees.

*imagem 3*
<p align="center">
<img alt="" src="">
</p>

*imagem 4*
<p align="center">
<img alt="" src="">
</p>

5.1.3. Caso a issue esteja associada a um projeto, certifique-se de movê-la para a lista *In progress* do board.

## 5.2. Implementando a issue no ambiente de desenvolvimento

5.2.1. Caso ainda não tenha feito, siga os passos descritos no [item 3][3] para criar um ambiente de desenvolvimento do projeto.

5.2.2. No diretório raiz do projeto, faça o check-out da branch `develop`.

`git checkout develop`

**Observação**: Caso ainda não tenha a branch `develop` no seu ambiente, faça o seu check-out do servidor remoto.

`git checkout -b develop origin/develop`

5.2.3. Realize um `pull` para certificar-se de que você está com as últimas alterações na sua branch local.

`git pull`

5.2.4. Crie uma nova branch para começar a trabalhar na issue selecionada. Para identificar qual a issue a branch está associada, o padrão de nome é `issue/<NUMERO>`, onde `<NUMERO>` é o número da issue.

*imagem 5*
<p align="center">
<img alt="" src="">
</p>

No exemplo acima, o comando de criação da branch seria:

`git checkout -b issue/8`

5.2.5. Ao realizar commits no seu trabalho, coloque ao final da mensagem a referência da issue que está sendo trabalhada. Isto ajudará a buscar os commits relacionados às issues analisadas.

`git commit -m "Cria modificador de mensagens e marca emails como lidos. Issue #30"`

*imagem 7*
<p align="center">
<img alt="" src="">
</p>

5.2.5. Em seguida, basta começar a trabalhar na issue para implementar a melhoria. Uma vez que você queira subir as alterações será necessário definir a branch no servidor Git para a qual as alterações serão salvas.

`git push -u origin issue/<NUMERO>`

5.2.6. Uma vez definida a branch, basta encaminhar os commits realizados através do comando de pull.

`git pull`

## 5.3. Encaminhando a sua implementação para revisão

Após concluir a implementação da melhoria e encaminhá-las para o servidor, abra um pull request solicitando a revisão e o aceite das melhorias.

*imagem 6*
<p align="center">
<img alt="" src="">
</p>

Ao selecionar a branch base, não esqueça de selecionar a branch `develop`. Na branch `compare`, selecione a branch na qual você estava trabalhando.

*imagem 8*
<p align="center">
<img alt="" src="">
</p>

**Observação**: Caso o GitHub informe que não é possível realizar o merge, atualize a sua branch com as últimas alterações da branch `develop` .

Clique no botão `Create pull request` para criar a solicitação.

*imagem 9*
<p align="center">
<img alt="" src="">
</p>

Na descrição do PR, coloque a issue que está sendo resolvida.

*imagem 10*
<p align="center">
<img alt="" src="">
</p>

Na área lateral direita, coloque o seu nome no campo Assignees.

*imagem 11*
<p align="center">
<img alt="" src="">
</p>

*imagem 12*
<p align="center">
<img alt="" src="">
</p>

Clique em `Create pull request` para concluir a solicitação.

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
[4]: ./como-executar-o-programa.md#4-como-executar-o-programa
[4.1]: ./como-executar-o-programa.md41-executando-o-programa-via-intellij
[4.2]: ./como-executar-o-programa.md#42-executando-o-programa-via-linha-de-comando
[4.3]: ./como-executar-o-programa.md#43-executando-o-programa-através-do-google-app-engine