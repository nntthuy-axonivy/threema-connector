# Threema Konnektor

Axon Ivy's Threema Konnektor ermöglicht es dir, Ende-zu-Ende-verschlüsselte Nachrichten zu senden, indem er die [Threema.Gateway API](https://threema.ch/en/gateway) von Threema integriert. Mit diesem Konnektor kannst du Nachrichten an einen oder mehrere Empfänger senden, wobei E-Mail, Telefonnummer oder ThreemaID als Identifikator verwendet werden.

Um Nachrichten zu senden, sind Zugangsdaten und Credits erforderlich. Die Zugangsdaten können kostenlos bei [Threema.Gateway](https://gateway.threema.ch/en/signup) erstellt werden. Credits können dann entsprechend der Nutzung erworben werden. Weitere Informationen findest du bei der [Threema.Gateway API](https://threema.ch/en/gateway).

## Demo

![Send to one recipient](./images/singleMessage.png)

![Send to multiple recipients](./images/multiMessage.png)

![Result screen](./images/resultScreen.png)

## Setup

1. Generate a new key pair using the "GenerateKeyPair" process.
2. Create "End-to-End Threema ID" at: [Request new ID](https://gateway.threema.ch/en/id-request) <br> 
Free credits for testing purposes can be requested at [support-gateway@threema.ch](mailto:support-gateway@threema.ch) <br>
3. Add the following variables to your Axon Ivy Project:

```
@variables.yaml@
```
