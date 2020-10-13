export default interface RoomLayout {
  name: string,
  x: number,
  y: number,
  width?: number,
  heigth?: number,
  north?: "door" | "window",
  east?:  "door" | "window",
  south?: "door" | "window",
  west?:  "door" | "window",
}

