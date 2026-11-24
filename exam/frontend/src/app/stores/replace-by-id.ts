export function replaceById<T extends { id: number }>(list: T[], item: T): T[] {
  const result: T[] = [];
  let found = false;
  for (const current of list) {
    if (current.id === item.id) {
      result.push(item);
      found = true;
    } else {
      result.push(current);
    }
  }
  if (!found) {
    result.push(item);
  }
  return result;
}
