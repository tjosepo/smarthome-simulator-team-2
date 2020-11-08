import React from 'react';
import { User } from '../../../../../models';
import { POST_DELETE_USER } from '../../../../../queries';

interface Props {
  userToDelete: User | undefined,
  users: User[],
  setUsers: React.Dispatch<React.SetStateAction<User[]>>
}

function DeleteUserModal({ userToDelete, users, setUsers }: Props) {
  const deleteUser = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!userToDelete) return;

    const form = e.target as HTMLFormElement;
    const data = new FormData(form);
    data.append('id', userToDelete.id.toString());

    const response = await fetch(POST_DELETE_USER, {
      method: 'POST',
      body: data
    });

    const users = await response.json() as User[];

    setUsers(users);
    (form.querySelector('[data-dismiss="modal"]') as HTMLElement).click();
  }

  return (
    <div className="DeleteUserModal modal fade" id="DeleteUserModal" tabIndex={-1} aria-labelledby="DeleteUserModalLabel" aria-hidden="true">
      <div className="modal-dialog">
        <form className="modal-content" onSubmit={(e) => deleteUser(e)}>
          <div className="modal-header">
            <h5 className="modal-title" id="DeleteUserModalLabel">Delete User</h5>
          </div>
          <div className="modal-body">
            <p>Are you sure you want to delete {userToDelete?.name}? This cannot be undone.</p>
          </div>
          <div className="modal-footer">
            <button type="button" className="btn btn-secondary" data-dismiss="modal">Cancel</button>
            <button type="submit" className="btn btn-danger">Delete</button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default DeleteUserModal;
