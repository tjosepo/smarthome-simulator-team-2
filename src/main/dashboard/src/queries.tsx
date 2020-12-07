/* Smart home simulator API */
export const POST_MOVE_USERS = 'http://localhost:7000/api/move-users';
export const POST_BLOCK_WINDOWS = 'http://localhost:7000/api/block-windows';
export const POST_START_SIMULATION = 'http://localhost:7000/api/start-simulation';
export const POST_STOP_SIMULATION = 'http://localhost:7000/api/stop-simulation';
export const POST_SET_SIMULATION_SPEED = 'http://localhost:7000/api/set-simulation-speed';
export const WEBSOCKET_CONSOLE_OUTPUT = 'ws://localhost:7000/websocket/console';
export const WEBSOCKET_SIMULATION = 'ws://localhost:7000/websocket/simulation';

/* Smart home house API */
export const POST_SET_HOUSE_LAYOUT = 'http://localhost:7000/api/set-house-layout';

/* Smart home parameters API */
export const POST_ADD_USER = 'http://localhost:7000/api/add-user';
export const POST_DELETE_USER = 'http://localhost:7000/api/delete-user';
export const POST_SET_OUTSIDE_TEMPERATURE = 'http://localhost:7000/api/set-outside-temperature';
export const POST_LOG_IN_AS = 'http://localhost:7000/api/log-in-as';

/* Smart home core API */
export const POST_TOGGLE_LIGHT = 'http://localhost:7000/api/toggle-light';
export const POST_TOGGLE_WINDOW = 'http://localhost:7000/api/toggle-window';
export const POST_TOGGLE_DOOR = 'http://localhost:7000/api/toggle-door';
export const POST_TOGGLE_AUTO_MODE = 'http://localhost:7000/api/toggle-auto-mode';

/* Smart home security API */
export const POST_AWAY_MODE = 'http://localhost:7000/api/away-mode';
export const POST_KEEP_LIGHT_ON = 'http://localhost:7000/api/keep-light-on';

/* Smart home heater API */
export const POST_SET_ZONE = 'http://localhost:7000/api/set-zone';
export const POST_SET_TEMPERATURE = 'http://localhost:7000/api/set-temperature';
export const POST_SET_ROOM_TO_ZONE = 'http://localhost:7000/api/set-room-to-zone';