export class Majagaba {
    constructor(
        public id: number,
        public life: number,
        public maxLife: number,
        public job: string,
        public dicePool: number[],
        public diceStocked: number[],
        public rerollLeft: number,
        public room: string
    ){}
}