const net = require('net')
const client = new net.Socket();

const forSend = {
    action: 'PIPE_ERROR_NAME',
    data: {
        test: 1
    }
}

const dataString = JSON.stringify(forSend);
let result = '';

beforeEach(done => {
    client.connect(8080, '127.0.0.1', async () => {
        client.on('data', (data) => {
            client.destroy();
            result = data.toString()
            done();
        })

        client.write(dataString);
    });
});

describe('Error task', () => {
    test('Received error result', () => {
        expect(result).toBe(JSON.stringify({error: true}));
    })
})
