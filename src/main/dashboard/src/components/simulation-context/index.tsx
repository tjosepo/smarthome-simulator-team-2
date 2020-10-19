import React, { Fragment } from 'react';
import { Room, User } from '../../models';
import EditSimulationModal from './modals/edit-simulation-modal';
import './style.scss';

interface Props {
  simulating: boolean,
  setSimulating: React.Dispatch<React.SetStateAction<boolean>>,
  users: User[],
  setUsers: React.Dispatch<React.SetStateAction<User[]>>,
  rooms: Room[]
}

function SimulationContext({ simulating, setSimulating, users, setUsers, rooms }: Props) {
  return (
    <div className="SimulationContext card">
      <div className="card-header">
        Simulation Context
        </div>
      <div className="card-body">
        <input type="checkbox" className="btn-check" id="btn-check-2" onInput={() => setSimulating(!simulating)} />
        <label className="btn btn-outline-primary w-100 mb-2" htmlFor="btn-check-2">
          {simulating ? "On" : "Off"}
        </label>
        <a href="#EditSimulationModal" data-toggle="modal">Edit simulation</a>
      </div>

      <Fragment>
        <EditSimulationModal {...{ users, rooms, setUsers }} />
      </Fragment>
    </div>
  );
}

export default SimulationContext;
