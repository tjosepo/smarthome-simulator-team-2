import React, { useEffect, useRef, useState } from 'react';
import { WEBSOCKET_CONSOLE_OUTPUT } from '../../queries';
import './style.scss';

function OutputConsole() {
  const [output, setOutput] = useState<string>('');
  const consoleEl = useRef<HTMLPreElement>(null);
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

  const scrollToBottom = () => {
    if (consoleEl.current) {
      consoleEl.current.scrollTop = consoleEl.current.scrollHeight;
    }
  }

  useEffect(newConnection, []);
  useEffect(scrollToBottom, [output]);

  return (
    <div className="OutputConsole">
      <pre ref={consoleEl}>
        <code>
          {output || 'Connecting to server...'}
        </code>
      </pre>
    </div>
  );
}

export default OutputConsole;
