import random
import statistics as stat
import math

def gloves(total_gloves):
    if total_gloves < 0:
        return 0;
    elif total_gloves == 0:
        return 1;
    else:
        subtract_four = gloves(total_gloves - 4)
        subtract_three = gloves(total_gloves - 3)
        subtract_two = gloves(total_gloves - 2)
        return subtract_two + subtract_three + subtract_four

class Die:

    def __init__(self, faces = 6, face_vals = list(range(1, 7))):
        self.faces = faces
        self.face_vals = face_vals
        if len(self.face_vals) != faces:
            self.face_vals = list(range(1, faces + 1))

    def roll(self):
        return self.face_vals[random.randint(0, self.faces - 1)]

def average_num_rolls(num_faces, num_trials):
    original_die = Die(num_faces)
    total, trials, currTrial = 0, num_trials, 0;
    all_nums = []
    while (currTrial < trials):
        this_roll = roll_til_same(original_die)
        total += this_roll
        all_nums += [this_roll]
        currTrial += 1
    mean = stat.mean(all_nums)
    stdev = stat.pstdev(all_nums)
    moe = (2.576 * stdev)/(math.sqrt(trials))
    return (mean, moe)

def roll_til_same(die):
    num_changes = 0;
    local_die = Die(die.faces, die.face_vals)
    while (not(all([local_die.face_vals[i] == local_die.face_vals[i - 1] for i in range(local_die.faces)]))):
        num_changes += 1
        local_die = Die(local_die.faces, [local_die.roll() for n in range(local_die.faces)])
    return num_changes
