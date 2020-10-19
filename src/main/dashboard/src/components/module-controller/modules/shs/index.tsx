import React, { Fragment, useState } from 'react';
import AddUserModal from './modals/add-user-modal';
import DeleteUserModal from './modals/delete-user-modal';
import { User, RoomLayout } from '../../../../models';
import 'bootstrap/js/dist/modal.js';
import './style.scss';

interface Props {
  simulating: boolean,
  users: User[],
  setUsers: React.Dispatch<React.SetStateAction<User[]>>,
  setHouseLayout: React.Dispatch<React.SetStateAction<RoomLayout[]>>
}
function SHS({ simulating, users, setUsers, setHouseLayout }: Props) {
  const [filename, setFilename] = useState<string>("");
  const [userToDelete, setUserToDelete] = useState<User>();

  const importLayoutFile = async (files: FileList | null) => {
    if (files === null) return;
    if (files.length === 0) return;
    const layoutFile = files[0];
    const layoutFileText = await layoutFile.text();
    setFilename(layoutFile.name);
    setHouseLayout(JSON.parse(layoutFileText));
  }

  return (
    <div className="SHS">
      <h5 className="card-title">Simulation Parameters</h5>

      <div className="row mb-2">
        <label className="col-sm-4 col-form-label" htmlFor="LayoutFile">House layout</label>
        <div className="col-sm-8 form-file">
          <input hidden aria-hidden type="file" className="form-file-input" accept="application/JSON" onChange={(e) => importLayoutFile(e.target.files)} id="LayoutFile" disabled={simulating} />
          <label className="form-file-label" htmlFor="LayoutFile">
            <span className="form-file-text">{filename}</span>
            <span className="form-file-button">Browse</span>
          </label>
        </div>
      </div>

      <div className="row mb-2">
        <label className="col-sm-4 col-form-label" htmlFor="Date">Date</label>
        <div className="col-sm-8">
          <input type="date" className="form-control" id="Date" disabled={simulating} />
        </div>
      </div>

      <div className="row mb-2">
        <label className="col-sm-4 col-form-label" htmlFor="Time">Time</label>
        <div className="col-sm-8">
          <input type="time" className="form-control" id="Time" disabled={simulating} />
        </div>
      </div>

      <div className="row mb-2">
        <label className="col-sm-4 col-form-label" htmlFor="Temperature">Outside temperature</label>
        <div className="col-sm-8">
          <div className="input-group">
            <input id="Temperature" type="number" className="form-control" aria-label="Temperature" />
            <span className="input-group-text">°C</span>
          </div>
        </div>
      </div>

      <div className="row mb-2">
        <label className="col-sm-4 col-form-label" htmlFor="Location">Location</label>
        <div className="col-sm-8">
          <input type="text" className="form-control" id="Location" disabled={simulating} />
        </div>
      </div>

      <div className="row mb-2">
        <label className="col-sm-4 col-form-label" htmlFor="LogInAs">Log in as</label>
        <div className="col-sm-8">
          <select className="form-select" aria-label="Log in as" id="LogInAs" name="logInAs" defaultValue="">
            <option value="" hidden></option>
            {users.map((user) =>
              <option key={user.id} value={user.id}>{user.name}</option>
            )}
          </select>
        </div>
      </div>

      <h5 className="card-title">Users</h5>

      <div className="list-group">
        {users.map((user) =>
          <div key={user.id} className="list-group-item">
            <div>
              <p className="h6 mb-0">{user.name}</p>
              <small className="text-muted">{user.role}</small>
            </div>

            {simulating ?
              <span className="text-secondary">
                Delete
              </span>
              :
              <a href="#DeleteUserModal" data-toggle="modal" className="text-danger" onClick={() => setUserToDelete(user)}>
                Delete
              </a>
            }
          </div>
        )}

        {simulating ?
          <span className="list-group-item list-group-item-action text-secondary">
            Add user
          </span>
          :
          <a data-target="#AddUserModal" className="list-group-item list-group-item-action text-secondary" data-toggle="modal">
            Add user
          </a>
        }
      </div>

      <Fragment>
        <AddUserModal {...{ users, setUsers }} />
        <DeleteUserModal {...{ userToDelete, users, setUsers }} />
      </Fragment>
    </div>
  );
}

export default SHS;
