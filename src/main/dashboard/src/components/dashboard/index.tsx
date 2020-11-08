import React, { useState } from 'react';
import { SimulationContext, ModuleController, HouseView } from '../';
import { User, Room } from '../../models';
import OutputConsole from '../output-console';
import EditSimulationModal from '../simulation-context/modals/edit-simulation-modal';
import './style.scss';

function Dashboard() {
  const [simulating, setSimulating] = useState<boolean>(false);
  const [awayMode, setAwayMode] = useState<boolean>(false);
  const [loggedAsId, setLoggedAsId] = useState<number>(-1);
  const [users, setUsers] = useState<User[]>([]);
  const [rooms, setRooms] = useState<Room[]>([]);

  return (
    <div className="Dashboard container-fluid">
      <SimulationContext {...{ simulating, setSimulating, loggedAsId, users }}>
        <EditSimulationModal {...{ users, rooms, setUsers, setRooms }} />
      </SimulationContext>
      <ModuleController {...{ simulating, users, awayMode, rooms, setUsers, setRooms, setLoggedAsId, setAwayMode }} />
      <HouseView {...{ users, rooms, simulating, awayMode }} />
      <OutputConsole />
    </div>
  );
}

export default Dashboard;
