import React, { useEffect, useState } from 'react';
import { WEBSOCKET_CONSOLE_OUTPUT } from '../../queries';
import './style.scss';

function OutputConsole() {
  const [output, setOutput] = useState<string>('');
  let message = ''; // Required. Acts a buffer for when multiple messages are received at once.
  let websocket: WebSocket;

  const newConnection = () => {
    websocket = new WebSocket(WEBSOCKET_CONSOLE_OUTPUT);
    websocket.onmessage = receiveMessage;
    websocket.onclose = receiveClose;
  }

  const receiveMessage = (event: MessageEvent) => {
    message += `${event.data}\n`;
    setOutput(message);
  }

  const receiveClose = (event: CloseEvent) => {
    message += `Connection with server lost. Trying to reconnect in 5 seconds.\n`;
    setOutput(message);
    setTimeout(newConnection, 5000);
  }

  useEffect(() => newConnection(), []);

  return (
    <div className="OutputConsole">
      <pre>
        <code>
          {output || 'Connecting to server...'}
        </code>
      </pre>
    </div>
  );
}

export default OutputConsole;
