export interface Message {
  content: string;
  owner: 'NEEDY' | 'VOLUNTEER';
  clientTimestamp?: number;
}
