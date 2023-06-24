import { Character } from "./character.model";
import { Player } from "./player.model";
import { Pod } from "./pod.model";

export class Expedition {
    constructor(
        public id: number,
        public name: string,
        public difficulty: number,
        public day: number,
        public hour: number,
        public minute: number,
        public pod: Pod,
        public captain: Player,
        public crew: Character[],
        public status: string
    ){}
}