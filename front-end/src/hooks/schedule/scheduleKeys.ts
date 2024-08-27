export const scheduleKeys = {
  all: ['schedules'] as const,
  kindergartenInfo: (kindergartenId: number) =>
    [...scheduleKeys.all, kindergartenId] as const,
  allSchedules: (kindergartenId: number, date: string) =>
    [...scheduleKeys.kindergartenInfo(kindergartenId), 'all', date] as const,
  banSchedules: (banId: number, date: string) =>
    [...scheduleKeys.all, 'ban', banId, date] as const,
};
