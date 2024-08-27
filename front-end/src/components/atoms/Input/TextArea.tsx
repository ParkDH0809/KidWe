import React, {ForwardedRef, forwardRef} from 'react';

interface TextAreaProps
  extends Omit<React.ComponentProps<'textarea'>, 'value' | 'onChange'> {
  value?: string;
  onChange?: (value: string) => void;
}

const TextArea = forwardRef(
  (
    {onChange, ...props}: TextAreaProps,
    ref?: ForwardedRef<HTMLTextAreaElement>
  ) => {
    const {value} = props;

    const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
      onChange?.(e.target.value);
    };

    const keyDownHandle = (e: React.KeyboardEvent) => {
      if (e.nativeEvent.isComposing) {
        return;
      }
    };

    return (
      <textarea
        className="box-border w-full h-full p-3 text-xs font-normal text-gray-300 transition-all border border-gray-200 rounded-md outline-none focus:outline-none focus:border-gray-200 focus:shadow-sm focus:shadow-gray-200"
        onChange={handleChange}
        onKeyDown={keyDownHandle}
        {...props}
        value={value}
        ref={ref}
      />
    );
  }
);

export default TextArea;
