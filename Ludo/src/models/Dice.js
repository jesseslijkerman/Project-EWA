export class Dice {
    eyes;


    constructor(eyes) {
        this.eyes = eyes;
    }

    static rollDice(){
    let random = Math.floor(Math.random() * 6 + 1);
    return random
    }

    static createDiceRoll(){
        const eyes = this.rollDice();
        return new Dice(eyes)
    }


}
