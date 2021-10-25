package com.gap.sourcing.smee.steps;

import com.gap.sourcing.smee.contexts.Context;
import com.gap.sourcing.smee.exceptions.GenericUserException;

public interface Step {
    Step execute(Context context) throws GenericUserException;
}
