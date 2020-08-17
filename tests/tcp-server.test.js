const net = require('net')
const client = new net.Socket();

(() => {
    client.connect(8080, '127.0.0.1', function() {
        client.on('data', (data) => {
            console.log(data.toString())
        })
        console.log('Connected');
        client.write('Hello, server! Love, Client.1');
    });
})()