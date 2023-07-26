import { ChatMessage } from "./chat-message.model";
import { Pod } from "./pod.model";
import { User } from "./user.model";

export class Expedition {
    constructor(
        public name: string,
        public difficulty: number,
        public day: number,
        public hour: number,
        public minute: number,
        public pod: Pod,
        public captain: User,
        public crew: User[],
        public water: number,
        public scrap: number,
        public depth: number,
        public messages: ChatMessage[],
        public status: string
    ){}
}