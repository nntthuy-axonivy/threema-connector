# Threema Connector
Axon Ivy's Threema Connector enables you to send end-to-end encrypted messages by integrating the [Threema.Gateway API](https://threema.ch/en/gateway) provided by Threema. With this connector you are able to send messages to one or more recipients using email, phone number or ThreemaID as identifier.

Credentials and credits are required to send messages. The credentials can be created free of charge at [Threema.Gateway](https://gateway.threema.ch/en/signup). Credits can then be bought in accordance with usage. More info can be found at [Threema.Gateway API](https://threema.ch/en/gateway).

## Demo
![Send to one recipient](./images/singleMessage.png)

![Send to multiple recipients](./images/multiMessage.png)

![Result screen](./images/resultScreen.png)

## Setup
This Connector requires an "End-to-End Threema ID". [Request new ID](https://gateway.threema.ch/en/id-request)
<br> 
For generating the keys to request a new Threema.Gateway ID refer to [Generate keys](https://gateway.threema.ch/en/developer/howto/create-keys/php).

To use the Threema Connector, add the following variables to your Axon Ivy Project:

```
Variables:
  connector:
   
    # Your Threema.Gateway ID 
    threemaId: ''
    
    # Your Threema.Gateway Secret
    secret: ''
    
    # Your private key associated with your Threema.Gateway ID
    privateKey: ''
```
