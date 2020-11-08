import React, { Fragment } from 'react';
import { User } from '../../models';
import './style.scss';

interface Props {
  loggedAsId: number,
  users: User[],
  simulating: boolean,
  setSimulating: React.Dispatch<React.SetStateAction<boolean>>,
  children: JSX.Element
}

function SimulationContext({ loggedAsId, users, simulating, setSimulating, children }: Props) {

  let loggedAs: User | undefined;
  if (loggedAsId !== -1) {
    loggedAs = users.find((user) => user.id === loggedAsId);
  }

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
        <a className="edit-simulation mb-4" href="#EditSimulationModal" data-toggle="modal">Edit simulation</a>

        <img className="silhouette mb-2" src="./images/user-silhouette.png" alt="User silhouette" />

        {loggedAs ? <>
          <p className="h6 mb-0">Role</p>
          <p className="user-role lead">{loggedAs.role}</p>

          <p className="h6 mb-0">Name</p>
          <p className="user-name lead">{loggedAs.name}</p>

          <p className="h6 mb-0">Location</p>
          <p className="user-location lead">{loggedAs.location ? loggedAs.location.name : 'Outside'}</p>
        </>
          : <p>Not logged in.</p>
        }

      </div>
      <Fragment>
        {children}
      </Fragment>
    </div>
  );
}

export default SimulationContext;
