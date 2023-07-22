import { Workshop } from "./workshop.model";

export class Room {
    constructor(
        public name: string,
        public health: number,
        public maxHealth: number,
        public workshops: Workshop[],
        public status: string
    ){}
}