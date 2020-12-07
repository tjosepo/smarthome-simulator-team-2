import React, { Fragment } from 'react';
import { User } from '../../models';
import { POST_SET_SIMULATION_SPEED } from '../../queries';
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

  const changeSimulationSpeed = (e: React.ChangeEvent<HTMLInputElement>) => {
    const input = e.target as HTMLInputElement;
    const value = input.value;
    const data = new FormData();
    data.append('simulationSpeed', value);
    fetch(POST_SET_SIMULATION_SPEED, {
      method: 'POST',
      body: data
    });
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

        <div className="btn-group mb-2" role="group" aria-label="Basic radio toggle button group">
          <input type="radio" className="btn-check" name="simulationSpeed" id="speed1" defaultValue="checked" value="1" onInput={changeSimulationSpeed} />
          <label className="btn btn-outline-primary" htmlFor="speed1">1×</label>

          <input type="radio" className="btn-check" name="simulationSpeed" id="speed2" value="2" onInput={changeSimulationSpeed} />
          <label className="btn btn-outline-primary" htmlFor="speed2">2×</label>

          <input type="radio" className="btn-check" name="simulationSpeed" id="speed3" value="3" onInput={changeSimulationSpeed} />
          <label className="btn btn-outline-primary" htmlFor="speed3">3×</label>

          <input type="radio" className="btn-check" name="simulationSpeed" id="speed5" value="5" onInput={changeSimulationSpeed} />
          <label className="btn btn-outline-primary" htmlFor="speed5">5×</label>

          <input type="radio" className="btn-check" name="simulationSpeed" id="speed10" value="10" onInput={changeSimulationSpeed} />
          <label className="btn btn-outline-primary" htmlFor="speed10">10×</label>
        </div>

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
