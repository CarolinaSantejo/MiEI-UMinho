import utils from '../../view/common/utils.js';
import enquirer from 'enquirer'
import acknowledgePrompts from '../../view/common/common_prompts.js';

function showBets(bettingSlip) {
    utils.clearScreen();
    console.log("===================== Betting Slip =====================");

    bettingSlip.forEach((bet, index) => {
        console.log("\n\n_________________________________")
        console.log(`β‘οΈ  Bet NrΒΊ ${index + 1} `);
        console.log(`Event ID: ${bet.eventID} (DEBUG ONLY)`);
        console.log(`ποΈ Date: ${bet.date}`);
        console.log(`π Time: ${bet.time}`);
        console.log(`π Type: ${bet.type}`);
        console.log(`βΉοΈ Team: ${bet.bettedTeam}`);
        console.log(`π Odd: ${bet.bettedOdd}`);
        console.log(`π° Quantity betted: ${bet.amount} ${bet.coin}`);
        console.log("\n");

    })
}

function showOptions() {
    console.log("\n\n");

    const questions = [
        {

            type: 'select',
            name: 'selectedOption',
            message: 'Options: ',
            choices: [
                { name: 'SUBMIT', message: 'Submit Betting Slip' },
                { name: 'REMOVE_BET', message: 'Cancel Bet' },
                { name: 'CLEAR', message: 'Clear Betting Slip' },
                { name: 'RETURN', message: 'Go Back' }
            ]
        }
    ];
    return enquirer.prompt(questions)
}

function showRemoveBet(betCount) {
    const questions = [
        {
            type: 'input',
            name: 'selectedBetNumber',
            message: 'Bet number:',
            float: false,
            validate: function(value) {
                if (isNaN(value) || parseInt(value) < -1 || parseInt(value) > betCount)
                   return 'Please enter a valid bet number!';
                return true;
            }
        }
    ];

    return enquirer.prompt(questions);
}



export default {
    showBets,
    showOptions,
    showRemoveBet,
}