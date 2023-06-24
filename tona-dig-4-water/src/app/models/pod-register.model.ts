import { Character } from "./character.model";
import { Player } from "./player.model";

export class PodRegister {
    constructor(
        public name: string,
        public difficulty: number,
        public captain: Player,
        public crew: Character,
        public playerMax: number,
        public password: string,
        public status: string
    ){}
}