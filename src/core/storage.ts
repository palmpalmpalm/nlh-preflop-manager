import type { SerializedAppState } from "./types";

const DB_NAME = "nlh-preflop-manager";
const DB_VERSION = 1;
const STORE_NAME = "snapshots";
const LATEST_KEY = "latest";

export async function saveLatestSnapshot(snapshot: SerializedAppState): Promise<void> {
  const db = await openDatabase();
  await requestToPromise(db.transaction(STORE_NAME, "readwrite").objectStore(STORE_NAME).put(snapshot, LATEST_KEY));
  db.close();
}

export async function loadLatestSnapshot(): Promise<SerializedAppState | null> {
  const db = await openDatabase();
  const snapshot = await requestToPromise<SerializedAppState | undefined>(
    db.transaction(STORE_NAME, "readonly").objectStore(STORE_NAME).get(LATEST_KEY)
  );
  db.close();
  return snapshot ?? null;
}

function openDatabase(): Promise<IDBDatabase> {
  return new Promise((resolve, reject) => {
    const request = indexedDB.open(DB_NAME, DB_VERSION);

    request.onupgradeneeded = () => {
      request.result.createObjectStore(STORE_NAME);
    };

    request.onerror = () => reject(request.error);
    request.onsuccess = () => resolve(request.result);
  });
}

function requestToPromise<T>(request: IDBRequest<T>): Promise<T> {
  return new Promise((resolve, reject) => {
    request.onerror = () => reject(request.error);
    request.onsuccess = () => resolve(request.result);
  });
}
