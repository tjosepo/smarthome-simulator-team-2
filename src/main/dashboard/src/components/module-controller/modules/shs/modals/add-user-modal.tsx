import React from 'react';
import { User } from '../../../../../models';
import { POST_ADD_USER } from '../../../../../queries';

interface Props {
  users: User[],
  setUsers: React.Dispatch<React.SetStateAction<User[]>>
}

function AddUserModal({ users, setUsers }: Props) {
  const addUser = async (e: React.FormEvent) => {
    e.preventDefault();

    const form = e.target as HTMLFormElement;
    const data = new FormData(form);

    const response = await fetch(POST_ADD_USER, {
      method: 'POST',
      body: data
    });

    const users = await response.json() as User[];
    console.log(users);

    setUsers(users);
    (form.querySelector('[data-dismiss="modal"]') as HTMLElement).click();
    form.reset();
  }

  return (
    <div className="AddUserModal modal fade" id="AddUserModal" tabIndex={-1} aria-labelledby="AddUserModalLabel" aria-hidden="true">
      <div className="modal-dialog">
        <form className="modal-content" onSubmit={(e) => addUser(e)}>
          <div className="modal-header">
            <h5 className="modal-title" id="AddUserModalLabel">Add User</h5>
          </div>
          <div className="modal-body">

            <div className="mb-2">
              <label htmlFor="Name" className="form-label">Name</label>
              <input type="text" className="form-control" id="Name" name="name" pattern="[A-Za-zÀ-ÖØ-öø-ÿ\\ \\-]+" required />
            </div>

            <div className="mb-2">
              <label htmlFor="Role" className="form-label">Role</label>
              <select className="form-select" aria-label="Role select" id="Role" name="role" required defaultValue="">
                <option value="" hidden></option>
                <option value="Parent">Parent</option>
                <option value="Child">Child</option>
                <option value="Guest">Guest</option>
                <option value="Stranger">Stranger</option>
              </select>
            </div>

          </div>
          <div className="modal-footer">
            <button type="button" className="btn btn-secondary" data-dismiss="modal">Cancel</button>
            <button type="submit" className="btn btn-primary">Add</button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default AddUserModal;
