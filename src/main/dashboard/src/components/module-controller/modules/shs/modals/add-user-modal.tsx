import React from 'react';
import { User } from '../../../../../models';

interface Props {
  users: User[],
  setUsers: React.Dispatch<React.SetStateAction<User[]>>
}

interface Form extends HTMLFormElement {
  username: HTMLInputElement,
  role: HTMLSelectElement
}

function AddUserModal({ users, setUsers }: Props) {
  const addUser = (e: React.FormEvent) => {
    e.preventDefault();

    const form = e.target as Form;
    const user: User = {
      id: 0,
      name: form.username.value,
      role: form.role.value
    }

    setUsers([...users, user]);
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
              <input type="text" className="form-control" id="Name" name="username" pattern="[A-Za-zÀ-ÖØ-öø-ÿ\\ \\-]+" required />
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
