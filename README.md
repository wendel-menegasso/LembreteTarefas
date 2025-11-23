Projeto feito em Java, usando EJB, JSF, Primefaces, e usando um FileSystem próprio ao invés de qualquer banco de dados, está acoplado ao uso do Wyldfly, porém se quiser usar outro servidor de aplicação, alterações devem ser feitas, é atrelado também ao twillio para enviar mensagens no Whatsapp. A versão do Java tem que ser 17 ou mais. As variáveis:
file-system-path=${DATABASE_PATH}
userId=${USER_ID}
token=${TOKEN}
yourNumber=${W_NUMBER}

Devem ser criadas no SO.

file-system-path=caminho do sistema de arquivos
user-id=id do usuário do twillio
token=token do twillio
yourNumber=número do Whatsapp que vai receber as mensagens.