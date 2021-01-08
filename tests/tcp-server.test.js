const net = require('net')
const client = new net.Socket();

(() => {
    client.connect(8080, '127.0.0.1', function() {
        client.on('data', (data) => {
            console.log(`INFO | Data received: ${data.toString()}`)
        })
        console.log('Connected');
        const forSend = {
            action: 'PIPE',
            data: {
                test: 1
            }
        }

        client.write(JSON.stringify(forSend));
        setTimeout(function () {
            client.write(JSON.stringify(forSend))
            console.log("Destroy after 1000 ms")
        }, 1500);

        setTimeout(function () {
            console.log("Destroyed")
            client.destroy();
        }, 2500);
    });
})()