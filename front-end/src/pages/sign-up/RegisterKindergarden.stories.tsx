import type {Meta, StoryObj} from '@storybook/react';
import RegisterKindergarden from '@/pages/sign-up/RegisterKindergarden';

const meta: Meta<typeof RegisterKindergarden> = {
  component: RegisterKindergarden,
};

export default meta;

type Story = StoryObj<typeof RegisterKindergarden>;

export const Default: Story = {
  args: {},
};
