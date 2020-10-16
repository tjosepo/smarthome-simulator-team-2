import React from 'react';
import { User } from '../../../models';

interface Props {
  users: User[]
}

function EditSimulationModal({ users }: Props) {

  const editSimulation = (e: React.FormEvent) => {
    e.preventDefault();

    alert('Not implemented yet.');
  }

  return (
    <div className="EditSimulationModal modal fade" id="EditSimulationModal" tabIndex={-1} aria-labelledby="EditSimulationModalLabel" aria-hidden="true">
      <div className="modal-dialog">
        <form className="modal-content" onSubmit={(e) => editSimulation(e)}>
          <div className="modal-header">
            <h5 className="modal-title" id="EditSimulationModalLabel">Edit Simulation</h5>
          </div>
          <div className="modal-body">

            <h5>Move Users</h5>
            {users.length > 0 ?
              users.map((user) =>
                <div className="row mb-2">
                  <label className="col-sm-4 col-form-label" htmlFor={user.name}>{user.name}</label>
                  <div className="col-sm-8">
                    <select className="form-select" aria-label="Default select example" id="Role" name={`${user.name}_location`} required defaultValue="-1">
                      <option value="-1">Outside</option>
                    </select>
                  </div>
                </div>
              )
              :
              <p>No users to move.</p>
            }

            <h5>Block Windows</h5>
            <p>No windows to block.</p>
          </div>
          <div className="modal-footer">
            <button type="button" className="btn btn-secondary" data-dismiss="modal">Cancel</button>
            <button type="submit" className="btn btn-primary">Save</button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default EditSimulationModal;
