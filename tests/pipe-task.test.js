const net = require('net')
const client = new net.Socket();

const forSend = {
    action: 'PIPE',
    data: {
        test: 1
    }
}

const dataString = JSON.stringify(forSend);
const results = [];

beforeEach(done => {
    client.connect(8080, '127.0.0.1', async () => {
        client.on('data', (data) => {
            client.destroy();
            results.push(data.toString());
            results.push(client.destroyed);
            done();
        })

        client.write(dataString);
    });
});

describe('Pipe task', () => {
    test('Received correct result', () => {
        expect(results[0]).toBe(JSON.stringify(forSend.data));
    })

    test('Connection was closed', () => {
        expect(results[1]).toBe(true);
    })
})
