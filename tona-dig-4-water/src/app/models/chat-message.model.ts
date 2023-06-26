import { User } from "./user.model";

export class ChatMessage {
    constructor(
        public user: User,
        public date: string,
        public contents: string
    ){}
}