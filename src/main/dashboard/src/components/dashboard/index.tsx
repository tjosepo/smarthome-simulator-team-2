import React, { useEffect, useState } from 'react';
import { SimulationContext, ModuleController, HouseView } from '../';
import { User, Room } from '../../models';
import { POST_START_SIMULATION, POST_STOP_SIMULATION, WEBSOCKET_SIMULATION } from '../../queries';
import OutputConsole from '../output-console';
import EditSimulationModal from '../simulation-context/modals/edit-simulation-modal';
import './style.scss';

function Dashboard() {
  const [simulating, setSimulating] = useState<boolean>(false);
  const [awayMode, setAwayMode] = useState<boolean>(false);
  const [outsideTemp, setOutsideTemp] = useState<number>(15.0);
  const [loggedAsId, setLoggedAsId] = useState<number>(-1);
  const [users, setUsers] = useState<User[]>([]);
  const [rooms, setRooms] = useState<Room[]>([]);
  let websocket: WebSocket;

  const newConnection = () => {
    websocket = new WebSocket(WEBSOCKET_SIMULATION);
    websocket.onmessage = receiveMessage;
    websocket.onclose = receiveClose;
  }

  const receiveMessage = (event: MessageEvent) => {
    const rooms = JSON.parse(event.data) as Room[];
    setRooms(rooms);
  }

  const receiveClose = (event: CloseEvent) => {
    setTimeout(newConnection, 5000);
  }

  useEffect(newConnection, []);

  useEffect(() => {
    if (simulating) fetch(POST_START_SIMULATION, { method: 'POST' });
    else fetch(POST_STOP_SIMULATION, { method: 'POST' });
  }, [simulating])


  return (
    <div className="Dashboard container-fluid">
      <SimulationContext {...{ simulating, setSimulating, loggedAsId, users }}>
        <EditSimulationModal {...{ users, rooms, setUsers, setRooms }} />
      </SimulationContext>
      <ModuleController {...{ simulating, users, awayMode, rooms, setUsers, setRooms, setLoggedAsId, setAwayMode, setOutsideTemp, loggedAsId }} />
      <HouseView {...{ users, rooms, simulating, awayMode, outsideTemp }} />
      <OutputConsole />
    </div>
  );
}

export default Dashboard;
