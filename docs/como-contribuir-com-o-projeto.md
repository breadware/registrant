# 5. Como contribuir com o projeto

Obrigado pelo interesse em colaborar com o projeto. Todos os pontos de melhoria estão mapeados nas [issues](https://github.com/breadware/registrant/issues) do repositório no GitHub. 

<p align="center">
<img width="600" alt="" src="https://user-images.githubusercontent.com/13152452/81430970-c48c1e00-9136-11ea-9c2c-bc574cff9f28.png">
</p>

**Observação**: Caso não exista nenhuma issue aberta, fale com o responsável do projeto para saber como contribuir.

## 5.1. Selecionando uma issue para trabalhar
 
5.1.1. Escolha uma issue no qual deseja trabalhar e clique no seu nome.

<p align="center">
<img width="600" alt="" src="https://user-images.githubusercontent.com/13152452/81430974-c524b480-9136-11ea-9576-305b7229c415.png">
</p>

5.1.2. Na área lateral direita, inclua o seu nome na lista de assignees.

<p align="center">
<img width="600" alt="" src="https://user-images.githubusercontent.com/13152452/81430976-c5bd4b00-9136-11ea-9065-a69d686cf47e.png">
</p>

<p align="center">
<img width="600" alt="" src="https://user-images.githubusercontent.com/13152452/81430980-c6ee7800-9136-11ea-8e30-b5549fba5938.png">
</p>

5.1.3. Caso a issue esteja associada a um projeto, certifique-se de movê-la para a lista *In progress* do board.

<p align="center">
<img width="600" alt="" src="https://user-images.githubusercontent.com/13152452/81430983-c7870e80-9136-11ea-9c20-8bd5a59d4145.png">
</p>

## 5.2. Implementando a issue no ambiente de desenvolvimento

5.2.1. Caso ainda não tenha feito, siga os passos descritos no [item 3][3] para criar um ambiente de desenvolvimento do projeto.

5.2.2. No diretório raiz do projeto, faça o check-out da branch `develop`.

`git checkout develop`

**Observação**: Caso ainda não tenha a branch `develop` no seu ambiente, faça o seu check-out do servidor remoto.

`git checkout -b develop origin/develop`

5.2.3. Realize um `pull` para certificar-se de que você está com as últimas alterações na sua branch local.

`git pull`

5.2.4. Crie uma nova branch para começar a trabalhar na issue selecionada. Para identificar qual a issue a branch está associada, o padrão de nome é `issue/<NUMERO>`, onde `<NUMERO>` é o número da issue.

<p align="center">
<img width="600" alt="" src="https://user-images.githubusercontent.com/13152452/81430988-c8b83b80-9136-11ea-9925-c8aa3924fdd0.png">
</p>

No exemplo acima, o comando de criação da branch seria:

`git checkout -b issue/8`

5.2.5. Ao realizar commits no seu trabalho, coloque ao final da mensagem a referência da issue que está sendo trabalhada. Isto ajudará a buscar os commits relacionados às issues analisadas.

`git commit -m "Cria modificador de mensagens e marca emails como lidos. Issue #30"`


<p align="center">
<img width="600" alt="" src="https://user-images.githubusercontent.com/13152452/81430990-c950d200-9136-11ea-8d3e-7798e75e59d5.png">
</p>

5.2.6. Em seguida, basta começar a trabalhar na issue para implementar a melhoria. Uma vez que você queira subir as alterações será necessário definir a branch no servidor Git para a qual as alterações serão salvas.

`git push -u origin issue/<NUMERO>`

5.2.7. Uma vez definida a branch, basta encaminhar os commits realizados através do comando de pull.

`git pull`

## 5.3. Encaminhando a sua implementação para revisão

5.3.1. Após concluir a implementação da melhoria e encaminhá-las para o servidor, abra um pull request solicitando a revisão e o aceite das melhorias.

<p align="center">
<img width="600" alt="" src="https://user-images.githubusercontent.com/13152452/81430989-c8b83b80-9136-11ea-81ec-0e351cf0e633.png">
</p>

5.3.2. Ao selecionar a branch base, não esqueça de selecionar a branch `develop`. Na branch `compare`, selecione a branch na qual você estava trabalhando.

<p align="center">
<img width="600" alt="" src="https://user-images.githubusercontent.com/13152452/81430992-c9e96880-9136-11ea-9afb-6c1c2347882d.png">
</p>

**Observação**: Caso o GitHub informe que não é possível realizar o merge, atualize a sua branch com as últimas alterações da branch `develop` .

Clique no botão `Create pull request` para criar a solicitação.

<p align="center">
<img width="600" alt="" src="https://user-images.githubusercontent.com/13152452/81430993-ca81ff00-9136-11ea-9115-1775cb4daf5e.png">
</p>

5.3.3. Na descrição do PR, coloque a issue que está sendo resolvida.

<p align="center">
<img width="400" alt="" src="https://user-images.githubusercontent.com/13152452/81430994-ca81ff00-9136-11ea-93a1-4048d69b2e22.png">
</p>

5.3.4. Na área lateral direita, coloque o seu nome na área `Assignees`.

<p align="center">
<img width="600" alt="" src="https://user-images.githubusercontent.com/13152452/81430997-cb1a9580-9136-11ea-870d-954484e0c438.png">
</p>

<p align="center">
<img width="600" alt="" src="https://user-images.githubusercontent.com/13152452/81430999-cbb32c00-9136-11ea-8bd2-4b8e8f3aeb58.png">
</p>

5.3.5. Clique em `Create pull request` para concluir a solicitação.

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
[5]: #5-como-contribuir-com-o-projeto
[5.1]: #51-selecionando-uma-issue-para-trabalhar
[5.2]: #52-implementando-a-issue-no-ambiente-de-desenvolvimento
[5.3]: #53-encaminhando-a-sua-implementação-para-revisão