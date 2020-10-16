export interface User {
  id?: number,
  location?: RoomLayout,
  name: string,
  role: string,
}

export interface RoomLayout {
  name: string,
  x: number,
  y: number,
  width?: number,
  heigth?: number,
  north?: "door" | "window",
  east?: "door" | "window",
  south?: "door" | "window",
  west?: "door" | "window",
}

