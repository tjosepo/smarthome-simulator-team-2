import React, { Fragment, useState } from 'react';
import AddUserModal from './modals/add-user-modal';
import DeleteUserModal from './modals/delete-user-modal';
import { User, Room } from '../../../../models';
import { saveAs } from 'file-saver';
import 'bootstrap/js/dist/modal.js';
import './style.scss';
import { POST_ADD_USER, POST_LOG_IN_AS, POST_SET_HOUSE_LAYOUT, POST_SET_OUTSIDE_TEMPERATURE } from '../../../../queries';

interface Props {
  simulating: boolean,
  users: User[],
  setUsers: React.Dispatch<React.SetStateAction<User[]>>,
  setRooms: React.Dispatch<React.SetStateAction<Room[]>>,
  setLoggedAsId: React.Dispatch<React.SetStateAction<number>>,
  setOutsideTemp: React.Dispatch<React.SetStateAction<number>>,
}
function SHS({ simulating, users, setUsers, setRooms, setLoggedAsId, setOutsideTemp }: Props) {
  const [filename, setFilename] = useState<string>("");
  const [userToDelete, setUserToDelete] = useState<User>();

  const importLayoutFile = async (files: FileList | null) => {
    if (files === null) return;
    if (files.length === 0) return;
    const layoutFile = files[0];
    const layoutFileData = await layoutFile.text();
    setFilename(layoutFile.name);

    const response = await fetch(POST_SET_HOUSE_LAYOUT, {
      method: 'POST',
      body: layoutFileData
    });

    const rooms = await response.json() as Room[];
    setRooms(rooms);
  }

  const importUsers = async (e: React.ChangeEvent<HTMLInputElement>) => {
    e.preventDefault();
    const files = e.target.files;
    if (files === null) return;
    if (files.length === 0) return;
    const usersFile = files[0];
    const usersFileText = await usersFile.text();
    const users = JSON.parse(usersFileText) as User[];

    users.forEach(async (user, i) => {
      const data = new FormData();
      data.append('name', user.name.toString());
      data.append('role', user.role.toString());

      const response = await fetch(POST_ADD_USER, {
        method: 'POST',
        body: data
      });

      if (i === users.length - 1) {
        const users = await response.json() as User[];
        setUsers(users);
      }
    });
  }

  const exportUsers = async () => {
    const str = JSON.stringify(users);
    const bytes = new TextEncoder().encode(str);
    const blob = new Blob([bytes], { type: "application/json;charset=utf-8" });
    saveAs(blob, 'users.json');
  }

  const changeOutsideTemperature = (e: React.ChangeEvent<HTMLInputElement>) => {
    e.preventDefault();
    const input = e.target as HTMLInputElement;
    const value = isNaN(input.valueAsNumber) ? 0 : input.valueAsNumber;
    const data = new FormData();
    data.append('temp', value.toString());
    fetch(POST_SET_OUTSIDE_TEMPERATURE, {
      method: 'POST',
      body: data
    })
    setOutsideTemp(value);
  }

  const logInAs = async (id: number) => {
    const data = new FormData();
    data.append('id', id.toString());

    const response = await fetch(POST_LOG_IN_AS, {
      method: 'POST',
      body: data
    });

    const user = await response.json() as User;
    setLoggedAsId(user.id);
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
            <input id="Temperature" type="number" className="form-control" aria-label="Temperature" disabled={simulating} defaultValue="15" onChange={changeOutsideTemperature} />
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
          <select className="form-select" aria-label="Log in as" id="LogInAs" name="logInAs" defaultValue="" disabled={simulating} onChange={(e) => logInAs(parseInt(e.target.value))}>
            <option value="-1" hidden></option>
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
          <span className="list-group-item text-secondary">
            Add user
          </span>
          :
          <a href="#AddUserModal" className="list-group-item list-group-item-action text-secondary" data-toggle="modal">
            Add user
          </a>
        }

        {users.length > 0
          ? <button type="button" className="list-group-item list-group-item-action text-secondary" onClick={exportUsers}>
            Export users
          </button>
          : <>
            <input hidden aria-hidden type="file" accept="application/JSON" onChange={importUsers} id="UserImport" disabled={simulating} />
            <label className="list-group-item list-group-item-action text-secondary" htmlFor="UserImport" >
              Import users
            </label>
          </>
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
