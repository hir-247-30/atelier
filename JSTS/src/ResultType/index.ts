import { err, ok } from 'neverthrow';
import type { Result } from 'neverthrow';

function maybeThrowError(): Result<string, Error> {
    if (Math.random() < 0.5) return ok('success');
    else return err(new Error('failure'));
}

const result = maybeThrowError();

if (result.isOk()) {
    const message = result.value;
    console.log(message);
}

if (result.isErr()) {
    const error = result.error.message;
    console.error(error);
}